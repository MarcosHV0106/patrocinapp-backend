package com.utp.patrocinapp.application.usecase.contrato;

import com.utp.patrocinapp.application.dto.contrato.CrearContratoRequest;
import com.utp.patrocinapp.application.dto.contrato.CrearContratoResponse;
import com.utp.patrocinapp.application.dto.contrato.MetaContratoCreadaResponse;
import com.utp.patrocinapp.application.dto.contrato.MetaSeleccionadaRequest;
import com.utp.patrocinapp.domain.model.Contrato;
import com.utp.patrocinapp.domain.model.FondoGarantia;
import com.utp.patrocinapp.domain.model.MetaContrato;
import com.utp.patrocinapp.domain.ports.input.CrearContratoInputPort;
import com.utp.patrocinapp.domain.ports.output.ContratoRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.FondoGarantiaRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.MetaContratoRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.PerfilDeportistaRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.PerfilNegocioRepositoryPort;
import com.utp.patrocinapp.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CrearContratoUseCase implements CrearContratoInputPort {

    private static final BigDecimal FACTOR_COMISION_NEGOCIO = new BigDecimal("1.10");

    private final ContratoRepositoryPort contratoRepository;
    private final FondoGarantiaRepositoryPort fondoGarantiaRepository;
    private final MetaContratoRepositoryPort metaContratoRepository;
    private final PerfilNegocioRepositoryPort perfilNegocioRepository;
    private final PerfilDeportistaRepositoryPort perfilDeportistaRepository;

    @Override
    public CrearContratoResponse ejecutar(CrearContratoRequest request) {

        if (perfilNegocioRepository.buscarPorId(request.getIdNegocio()).isEmpty()) {
            throw new BusinessException("El negocio indicado no existe.");
        }

        if (perfilDeportistaRepository.buscarPorId(request.getIdDeportista()).isEmpty()) {
            throw new BusinessException("El deportista indicado no existe.");
        }

        BigDecimal montoTotalNegocio = request.getMetas()
                .stream()
                .map(MetaSeleccionadaRequest::getMontoDeportista)
                .map(monto -> monto.multiply(FACTOR_COMISION_NEGOCIO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Contrato contrato = Contrato.crear(
                request.getIdNegocio(),
                request.getIdDeportista(),
                montoTotalNegocio
        );

        contrato = contratoRepository.guardar(contrato);

        FondoGarantia fondo = FondoGarantia.crear(
                contrato.getIdContrato(),
                montoTotalNegocio
        );

        fondoGarantiaRepository.guardar(fondo);

        List<MetaContratoCreadaResponse> metasCreadas = new ArrayList<>();

        for (MetaSeleccionadaRequest item : request.getMetas()) {
            BigDecimal montoNegocio = item.getMontoDeportista()
                    .multiply(FACTOR_COMISION_NEGOCIO);

            MetaContrato meta = MetaContrato.crear(
                    item.getIdPlantilla(),
                    item.getDescripcionAcordada(),
                    item.getMontoDeportista(),
                    montoNegocio,
                    item.getComentarioDeportista()
            );

            meta.setIdContrato(contrato.getIdContrato());

            MetaContrato metaGuardada = metaContratoRepository.guardar(meta);

            metasCreadas.add(
                    MetaContratoCreadaResponse.builder()
                            .idMeta(metaGuardada.getIdMetaContrato())
                            .idPlantilla(metaGuardada.getIdPlantilla())
                            .descripcionAcordada(metaGuardada.getDescripcionAcordada())
                            .montoDeportista(metaGuardada.getMontoDeportista())
                            .estado(metaGuardada.getEstado())
                            .build()
            );
        }

        return CrearContratoResponse.builder()
                .idContrato(contrato.getIdContrato())
                .montoTotal(montoTotalNegocio)
                .estado(contrato.getEstado())
                .metas(metasCreadas)
                .build();
    }
}