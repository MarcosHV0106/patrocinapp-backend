package com.utp.patrocinapp.application.dto.contrato;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CrearContratoRequest {

    @Deprecated
    private Integer idNegocio;

    @NotNull
    private Integer idDeportista;

    @Valid
    @NotEmpty
    private List<MetaSeleccionadaRequest> metas;

}
