package com.utp.patrocinapp.application.dto.contrato;

import com.utp.patrocinapp.domain.enums.EstadoContrato;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class CrearContratoResponse {

    private Integer idContrato;

    private BigDecimal montoTotal;

    private EstadoContrato estado;

    private List<MetaContratoCreadaResponse> metas;

}