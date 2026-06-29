package com.utp.patrocinapp.application.usecase.meta;

import com.utp.patrocinapp.application.dto.meta.AprobarMetaResponse;
import com.utp.patrocinapp.domain.enums.EstadoMeta;
import com.utp.patrocinapp.domain.model.FondoGarantia;
import com.utp.patrocinapp.domain.model.MetaContrato;
import com.utp.patrocinapp.domain.model.Transaccion;
import com.utp.patrocinapp.domain.ports.input.AprobarMetaInputPort;
import com.utp.patrocinapp.domain.ports.output.FondoGarantiaRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.MetaContratoRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.TransaccionRepositoryPort;
import com.utp.patrocinapp.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AprobarMetaUseCase implements AprobarMetaInputPort {

    private final MetaContratoRepositoryPort metaRepository;
    private final FondoGarantiaRepositoryPort fondoRepository;
    private final TransaccionRepositoryPort transaccionRepository;

    @Override
    public AprobarMetaResponse ejecutar(Integer idMeta) {

        MetaContrato meta = metaRepository.buscarPorId(idMeta)
                .orElseThrow(() ->
                        new BusinessException("La meta no existe."));

        FondoGarantia fondo = fondoRepository.buscarPorContrato(meta.getIdContrato())
                .orElseThrow(() ->
                        new BusinessException("No existe el fondo de garantía."));

        meta.setEstado(EstadoMeta.PAGADA);
        meta.setFechaActualizacion(LocalDateTime.now());

        fondo.setMontoCongelado(
                fondo.getMontoCongelado()
                        .subtract(meta.getMontoNegocio())
        );
        fondo.setUltimaActualizacion(LocalDateTime.now());

        Transaccion transaccion = Transaccion.crear(
                meta.getIdContrato(),
                meta.getIdMetaContrato(),
                meta.getMontoDeportista(),
                meta.getMontoNegocio().subtract(meta.getMontoDeportista())
        );

        metaRepository.guardar(meta);
        fondoRepository.guardar(fondo);
        transaccionRepository.guardar(transaccion);

        return AprobarMetaResponse.builder()
                .idMeta(meta.getIdMetaContrato())
                .estado(meta.getEstado().name())
                .montoLiberado(meta.getMontoDeportista())
                .build();
    }
}