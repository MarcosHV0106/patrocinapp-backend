package com.utp.patrocinapp.application.dto.meta;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarEvidenciaRequest {

    @NotBlank(message = "La URL de la evidencia es obligatoria.")
    private String urlEvidencia;

}