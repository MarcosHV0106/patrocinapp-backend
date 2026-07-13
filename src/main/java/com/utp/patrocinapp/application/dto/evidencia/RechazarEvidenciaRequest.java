package com.utp.patrocinapp.application.dto.evidencia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RechazarEvidenciaRequest {
    @NotBlank(message = "El motivo de rechazo es obligatorio.")
    @Size(max = 1000, message = "El motivo no puede superar 1000 caracteres.")
    private String motivo;
}
