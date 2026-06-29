package com.utp.patrocinapp.application.dto.meta;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AprobarMetaResponse {

    private Integer idMeta;

    private String estado;

    private BigDecimal montoLiberado;

}