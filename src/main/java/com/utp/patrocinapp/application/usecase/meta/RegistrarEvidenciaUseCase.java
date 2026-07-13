package com.utp.patrocinapp.application.usecase.meta;

import com.utp.patrocinapp.application.dto.meta.RegistrarEvidenciaRequest;
import com.utp.patrocinapp.application.service.AutorizacionRecursosService;
import com.utp.patrocinapp.domain.enums.Rol;
import com.utp.patrocinapp.domain.model.Contrato;
import com.utp.patrocinapp.domain.model.MetaContrato;
import com.utp.patrocinapp.domain.model.UsuarioAutenticado;
import com.utp.patrocinapp.domain.ports.input.RegistrarEvidenciaInputPort;
import com.utp.patrocinapp.domain.ports.output.MetaContratoRepositoryPort;
import com.utp.patrocinapp.shared.exception.BusinessException;
import com.utp.patrocinapp.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Compatibilidad temporal. El flujo principal usa multipart en /api/metas/{id}/evidencias. */
@Deprecated
@Service
@RequiredArgsConstructor
public class RegistrarEvidenciaUseCase implements RegistrarEvidenciaInputPort {
    private final MetaContratoRepositoryPort metaRepository;
    private final AutorizacionRecursosService autorizacion;

    @Override
    @Transactional
    public void ejecutar(Integer idMeta, RegistrarEvidenciaRequest request) {
        UsuarioAutenticado actor = autorizacion.actorConRol(Rol.DEPORTISTA);
        MetaContrato meta = metaRepository.buscarPorIdParaActualizar(idMeta)
                .orElseThrow(() -> new ResourceNotFoundException("META_INEXISTENTE", "La meta no existe."));
        Contrato contrato = autorizacion.contratoDeMeta(meta);
        autorizacion.validarDeportista(actor, contrato);
        if (!meta.puedeRecibirEvidencia()) {
            throw new BusinessException(HttpStatus.CONFLICT, "META_NO_ADMITE_EVIDENCIA",
                    "La meta no admite una evidencia en su estado actual.");
        }
        meta.setUrlEvidencia(request.getUrlEvidencia());
        meta.marcarEnRevision(null);
        metaRepository.guardar(meta);
    }
}
