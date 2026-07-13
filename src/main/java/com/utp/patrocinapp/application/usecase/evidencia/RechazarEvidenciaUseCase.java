package com.utp.patrocinapp.application.usecase.evidencia;

import com.utp.patrocinapp.application.dto.evidencia.EvidenciaResponse;
import com.utp.patrocinapp.application.service.AutorizacionRecursosService;
import com.utp.patrocinapp.domain.enums.Rol;
import com.utp.patrocinapp.domain.enums.TipoNotificacion;
import com.utp.patrocinapp.domain.model.*;
import com.utp.patrocinapp.domain.ports.input.RechazarEvidenciaInputPort;
import com.utp.patrocinapp.domain.ports.output.*;
import com.utp.patrocinapp.shared.exception.BusinessException;
import com.utp.patrocinapp.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RechazarEvidenciaUseCase implements RechazarEvidenciaInputPort {
    private final EvidenciaRepositoryPort evidenciaRepository;
    private final MetaContratoRepositoryPort metaRepository;
    private final NotificacionRepositoryPort notificacionRepository;
    private final AuditoriaRepositoryPort auditoriaRepository;
    private final AutorizacionRecursosService autorizacion;

    @Override
    @Transactional
    public EvidenciaResponse ejecutar(Long idEvidencia, String motivo) {
        UsuarioAutenticado actor = autorizacion.actorConRol(Rol.NEGOCIO);
        Evidencia evidencia = evidenciaRepository.buscarPorIdParaActualizar(idEvidencia)
                .orElseThrow(() -> new ResourceNotFoundException("EVIDENCIA_INEXISTENTE", "La evidencia no existe."));
        MetaContrato meta = metaRepository.buscarPorIdParaActualizar(evidencia.getIdMetaContrato())
                .orElseThrow(() -> new ResourceNotFoundException("META_INEXISTENTE", "La meta no existe."));
        Contrato contrato = autorizacion.contratoDeMeta(meta);
        autorizacion.validarNegocio(actor, contrato);

        try {
            evidencia.rechazar(motivo, actor.id());
            meta.marcarRechazada();
        } catch (IllegalArgumentException ex) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "RECHAZO_SIN_MOTIVO", ex.getMessage());
        } catch (IllegalStateException ex) {
            throw new BusinessException(HttpStatus.CONFLICT, "EVIDENCIA_YA_REVISADA", ex.getMessage());
        }

        Evidencia guardada = evidenciaRepository.guardar(evidencia);
        metaRepository.guardar(meta);
        notificacionRepository.guardar(Notificacion.crear(contrato.getIdDeportista(),
                TipoNotificacion.EVIDENCIA_RECHAZADA,
                "Tu evidencia fue rechazada. Revisa el motivo y envía una nueva versión.",
                "EVIDENCIA", evidencia.getIdEvidencia()));
        auditoriaRepository.guardar(AuditoriaAccion.registrar(actor.id(), "EVIDENCIA_RECHAZADA", "EVIDENCIA",
                evidencia.getIdEvidencia(), "EXITOSO", "Motivo registrado sin contenido binario."));
        return EvidenciaResponse.from(guardada);
    }
}
