package com.utp.patrocinapp.application.dto.contrato;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MetaSeleccionadaRequest {

    @NotNull
    private Integer idPlantilla;

    @NotBlank
    private String descripcionAcordada;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal montoDeportista;

    private String comentarioDeportista;

}