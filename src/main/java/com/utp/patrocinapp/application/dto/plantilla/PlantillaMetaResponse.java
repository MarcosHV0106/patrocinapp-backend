package com.utp.patrocinapp.application.dto.plantilla;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PlantillaMetaResponse {

    private Integer id;

    private String nombreMeta;

    private String descripcionSugerida;

    private String tipoEntregable;

    private BigDecimal precioSugerido;

}