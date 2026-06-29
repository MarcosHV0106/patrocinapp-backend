package com.utp.patrocinapp.application.usecase.meta;

import com.utp.patrocinapp.application.dto.meta.RegistrarEvidenciaRequest;
import com.utp.patrocinapp.domain.enums.EstadoMeta;
import com.utp.patrocinapp.domain.model.MetaContrato;
import com.utp.patrocinapp.domain.ports.input.RegistrarEvidenciaInputPort;
import com.utp.patrocinapp.domain.ports.output.MetaContratoRepositoryPort;
import com.utp.patrocinapp.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarEvidenciaUseCase implements RegistrarEvidenciaInputPort {

    private final MetaContratoRepositoryPort metaContratoRepository;

    @Override
    public void ejecutar(Integer idMeta,
                         RegistrarEvidenciaRequest request) {

        MetaContrato meta = metaContratoRepository.buscarPorId(idMeta)
                .orElseThrow(() ->
                        new BusinessException("La meta no existe."));

        meta.setUrlEvidencia(request.getUrlEvidencia());
        meta.setEstado(EstadoMeta.EN_REVISION);

        metaContratoRepository.guardar(meta);
    }
}