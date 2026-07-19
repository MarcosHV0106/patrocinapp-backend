package com.utp.patrocinapp.application.usecase.evidencia;

import com.utp.patrocinapp.application.dto.evidencia.ArchivoEvidenciaCommand;
import com.utp.patrocinapp.application.dto.evidencia.EvidenciaResponse;
import com.utp.patrocinapp.application.service.ArchivoValidado;
import com.utp.patrocinapp.application.service.AutorizacionRecursosService;
import com.utp.patrocinapp.application.service.ValidadorArchivoEvidencia;
import com.utp.patrocinapp.domain.enums.EstadoEvidencia;
import com.utp.patrocinapp.domain.enums.Rol;
import com.utp.patrocinapp.domain.enums.TipoNotificacion;
import com.utp.patrocinapp.domain.model.*;
import com.utp.patrocinapp.domain.ports.input.EnviarEvidenciaInputPort;
import com.utp.patrocinapp.domain.ports.output.*;
import com.utp.patrocinapp.shared.exception.BusinessException;
import com.utp.patrocinapp.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnviarEvidenciaUseCase implements EnviarEvidenciaInputPort {
    private final MetaContratoRepositoryPort metaRepository;
    private final EvidenciaRepositoryPort evidenciaRepository;
    private final ArchivoEvidenciaStoragePort storagePort;
    private final NotificacionRepositoryPort notificacionRepository;
    private final AuditoriaRepositoryPort auditoriaRepository;
    private final AutorizacionRecursosService autorizacion;
    private final ValidadorArchivoEvidencia validador;

    @Override
    @Transactional
    public EvidenciaResponse ejecutar(Integer idMeta, ArchivoEvidenciaCommand command) {
        UsuarioAutenticado actor = autorizacion.actorConRol(Rol.DEPORTISTA);
        MetaContrato meta = metaRepository.buscarPorIdParaActualizar(idMeta)
                .orElseThrow(() -> new ResourceNotFoundException("META_INEXISTENTE", "La meta no existe."));
        Contrato contrato = autorizacion.contratoDeMeta(meta);
        autorizacion.validarDeportista(actor, contrato);

        if (!meta.puedeRecibirEvidencia()) {
            throw new BusinessException(HttpStatus.CONFLICT, "META_NO_ADMITE_EVIDENCIA",
                    "La meta no admite una nueva evidencia en su estado actual.");
        }
        if (evidenciaRepository.existePorMetaYEstado(idMeta, EstadoEvidencia.EN_REVISION)) {
            throw new BusinessException(HttpStatus.CONFLICT, "EVIDENCIA_EN_REVISION",
                    "Ya existe una evidencia pendiente de revisión para esta meta.");
        }
        if (command.comentario() != null && command.comentario().length() > 1000) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "COMENTARIO_DEMASIADO_LARGO",
                    "El comentario no puede superar 1000 caracteres.");
        }

        ArchivoValidado archivo = validador.validar(command);
        if (evidenciaRepository.existeHashParaMeta(idMeta, archivo.hashSha256())) {
            throw new BusinessException(HttpStatus.CONFLICT, "ARCHIVO_DUPLICADO",
                    "Este mismo archivo ya fue enviado para la meta.");
        }

        int intento = Math.toIntExact(evidenciaRepository.contarPorMeta(idMeta) + 1);
        Evidencia evidencia = Evidencia.crear(idMeta, intento, archivo.nombreOriginal(), archivo.tipoMime(),
                archivo.tamanioBytes(), archivo.hashSha256(), normalizar(command.comentario()));
        Evidencia guardada = evidenciaRepository.guardar(evidencia);
        storagePort.guardar(guardada.getIdEvidencia(), archivo.contenido());
        meta.marcarEnRevision(normalizar(command.comentario()));
        metaRepository.guardar(meta);

        TipoNotificacion tipo = intento == 1
                ? TipoNotificacion.EVIDENCIA_ENVIADA : TipoNotificacion.EVIDENCIA_REENVIADA;
        notificacionRepository.guardar(Notificacion.crear(contrato.getIdNegocio(), tipo,
                intento == 1 ? "Se envió una evidencia para revisión."
                        : "Se reenvió una evidencia corregida para revisión.",
                "EVIDENCIA", guardada.getIdEvidencia()));
        auditoriaRepository.guardar(AuditoriaAccion.registrar(actor.id(), tipo.name(), "EVIDENCIA",
                guardada.getIdEvidencia(), "EXITOSO", "Intento " + intento + "; hash " + archivo.hashSha256()));
        return EvidenciaResponse.from(guardada);
    }

    private String normalizar(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
