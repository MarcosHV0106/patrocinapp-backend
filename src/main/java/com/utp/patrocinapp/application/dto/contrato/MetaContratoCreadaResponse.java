package com.utp.patrocinapp.application.dto.contrato;

import com.utp.patrocinapp.domain.enums.EstadoMeta;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class MetaContratoCreadaResponse {

    private Integer idMeta;

    private Integer idPlantilla;

    private String descripcionAcordada;

    private BigDecimal montoDeportista;

    private EstadoMeta estado;

}