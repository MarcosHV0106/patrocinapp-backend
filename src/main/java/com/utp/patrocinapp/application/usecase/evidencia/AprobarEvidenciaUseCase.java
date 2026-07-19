package com.utp.patrocinapp.application.usecase.evidencia;

import com.utp.patrocinapp.application.dto.evidencia.AprobarEvidenciaResponse;
import com.utp.patrocinapp.application.service.AutorizacionRecursosService;
import com.utp.patrocinapp.domain.enums.EstadoEvidencia;
import com.utp.patrocinapp.domain.enums.EstadoMeta;
import com.utp.patrocinapp.domain.enums.Rol;
import com.utp.patrocinapp.domain.enums.TipoNotificacion;
import com.utp.patrocinapp.domain.model.*;
import com.utp.patrocinapp.domain.ports.input.AprobarEvidenciaInputPort;
import com.utp.patrocinapp.domain.ports.output.*;
import com.utp.patrocinapp.shared.exception.BusinessException;
import com.utp.patrocinapp.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AprobarEvidenciaUseCase implements AprobarEvidenciaInputPort {
    private final EvidenciaRepositoryPort evidenciaRepository;
    private final MetaContratoRepositoryPort metaRepository;
    private final ContratoRepositoryPort contratoRepository;
    private final FondoGarantiaRepositoryPort fondoRepository;
    private final TransaccionRepositoryPort transaccionRepository;
    private final NotificacionRepositoryPort notificacionRepository;
    private final AuditoriaRepositoryPort auditoriaRepository;
    private final AutorizacionRecursosService autorizacion;

    @Override
    @Transactional
    public AprobarEvidenciaResponse ejecutar(Long idEvidencia) {
        UsuarioAutenticado actor = autorizacion.actorConRol(Rol.NEGOCIO);
        Evidencia evidencia = evidenciaRepository.buscarPorIdParaActualizar(idEvidencia)
                .orElseThrow(() -> new ResourceNotFoundException("EVIDENCIA_INEXISTENTE", "La evidencia no existe."));
        MetaContrato meta = metaRepository.buscarPorIdParaActualizar(evidencia.getIdMetaContrato())
                .orElseThrow(() -> new ResourceNotFoundException("META_INEXISTENTE", "La meta no existe."));
        Contrato contrato = contratoRepository.buscarPorIdParaActualizar(meta.getIdContrato())
                .orElseThrow(() -> new ResourceNotFoundException("CONTRATO_INEXISTENTE", "El contrato no existe."));
        autorizacion.validarNegocio(actor, contrato);

        if (evidencia.getEstado() != EstadoEvidencia.EN_REVISION || meta.getEstado() != EstadoMeta.EN_REVISION) {
            throw new BusinessException(HttpStatus.CONFLICT, "EVIDENCIA_YA_REVISADA",
                    "La evidencia ya fue revisada o la meta cambió de estado.");
        }
        if (transaccionRepository.existePorMeta(meta.getIdMetaContrato())) {
            throw new BusinessException(HttpStatus.CONFLICT, "OPERACION_DUPLICADA",
                    "La meta ya tiene una transacción registrada.");
        }

        FondoGarantia fondo = fondoRepository.buscarPorContratoParaActualizar(meta.getIdContrato())
                .orElseThrow(() -> new ResourceNotFoundException("FONDO_INEXISTENTE",
                        "No existe el fondo de garantía del contrato."));
        if (fondo.getMontoCongelado().compareTo(meta.getMontoNegocio()) < 0) {
            throw new BusinessException(HttpStatus.CONFLICT, "FONDO_INSUFICIENTE",
                    "El fondo de garantía no tiene saldo suficiente.");
        }

        BigDecimal comision = meta.getMontoNegocio().subtract(meta.getMontoDeportista());
        try {
            evidencia.aprobar(actor.id());
            meta.marcarAprobada();
            fondo.liberar(meta.getMontoNegocio());
        } catch (IllegalStateException ex) {
            throw new BusinessException(HttpStatus.CONFLICT, "ESTADO_OPERACION_INVALIDO", ex.getMessage());
        }

        evidenciaRepository.guardar(evidencia);
        metaRepository.guardar(meta);
        fondoRepository.guardar(fondo);
        transaccionRepository.guardar(Transaccion.crear(meta.getIdContrato(), meta.getIdMetaContrato(),
                meta.getMontoDeportista(), comision));
        meta.marcarPagada();
        metaRepository.guardar(meta);

        notificacionRepository.guardar(Notificacion.crear(contrato.getIdDeportista(),
                TipoNotificacion.EVIDENCIA_APROBADA, "Tu evidencia fue aprobada.",
                "EVIDENCIA", evidencia.getIdEvidencia()));
        notificacionRepository.guardar(Notificacion.crear(contrato.getIdDeportista(),
                TipoNotificacion.META_PAGADA, "El pago de la meta fue liberado.",
                "META", meta.getIdMetaContrato()));

        if (metaRepository.contarNoPagadasPorContrato(contrato.getIdContrato()) == 0) {
            contrato.finalizar();
            contratoRepository.guardar(contrato);
            notificacionRepository.guardar(Notificacion.crear(contrato.getIdDeportista(),
                    TipoNotificacion.CONTRATO_FINALIZADO, "Todas las metas fueron pagadas; el contrato finalizó.",
                    "CONTRATO", contrato.getIdContrato()));
            notificacionRepository.guardar(Notificacion.crear(contrato.getIdNegocio(),
                    TipoNotificacion.CONTRATO_FINALIZADO, "Todas las metas fueron pagadas; el contrato finalizó.",
                    "CONTRATO", contrato.getIdContrato()));
        }

        auditoriaRepository.guardar(AuditoriaAccion.registrar(actor.id(), "EVIDENCIA_APROBADA_Y_PAGADA",
                "EVIDENCIA", evidencia.getIdEvidencia(), "EXITOSO",
                "Meta " + meta.getIdMetaContrato() + "; transacción única por restricción."));
        return new AprobarEvidenciaResponse(evidencia.getIdEvidencia(), meta.getIdMetaContrato(),
                meta.getEstado().name(), meta.getMontoDeportista(), comision,
                fondo.getMontoCongelado(), contrato.getEstado());
    }
}
