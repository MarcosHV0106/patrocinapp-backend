package com.utp.patrocinapp.application.usecase.contrato;

import com.utp.patrocinapp.application.dto.contrato.CrearContratoRequest;
import com.utp.patrocinapp.application.dto.contrato.CrearContratoResponse;
import com.utp.patrocinapp.application.dto.contrato.MetaSeleccionadaRequest;
import com.utp.patrocinapp.domain.model.Contrato;
import com.utp.patrocinapp.domain.model.FondoGarantia;
import com.utp.patrocinapp.domain.model.MetaContrato;
import com.utp.patrocinapp.domain.ports.input.CrearContratoInputPort;
import com.utp.patrocinapp.domain.ports.output.ContratoRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.FondoGarantiaRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.MetaContratoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class CrearContratoUseCase implements CrearContratoInputPort {

    private final ContratoRepositoryPort contratoRepository;
    private final FondoGarantiaRepositoryPort fondoGarantiaRepository;
    private final MetaContratoRepositoryPort metaContratoRepository;

    @Override
    public CrearContratoResponse ejecutar(CrearContratoRequest request) {

        // =============================
        // Calcular monto total
        // =============================

        BigDecimal montoTotal = request.getMetas()
                .stream()
                .map(MetaSeleccionadaRequest::getMontoDeportista)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // =============================
        // Crear contrato
        // =============================

        Contrato contrato = Contrato.crear(
                request.getIdNegocio(),
                request.getIdDeportista(),
                montoTotal
        );

        contrato = contratoRepository.guardar(contrato);

        // =============================
        // Crear fondo de garantía
        // =============================

        FondoGarantia fondo = FondoGarantia.crear(
                contrato.getIdContrato(),
                montoTotal
        );

        fondoGarantiaRepository.guardar(fondo);

        // =============================
        // Registrar metas
        // =============================

        for (MetaSeleccionadaRequest item : request.getMetas()) {

            BigDecimal montoNegocio =
                    item.getMontoDeportista()
                            .multiply(new BigDecimal("1.10"));

            MetaContrato meta = MetaContrato.crear(
                    item.getIdPlantilla(),
                    item.getDescripcionAcordada(),
                    item.getMontoDeportista(),
                    montoNegocio,
                    item.getComentarioDeportista()
            );

            meta.setIdContrato(contrato.getIdContrato());

            metaContratoRepository.guardar(meta);

        }

        // =============================
        // Respuesta
        // =============================

        return CrearContratoResponse.builder()
                .idContrato(contrato.getIdContrato())
                .montoTotal(montoTotal)
                .estado(contrato.getEstado())
                .build();

    }

}