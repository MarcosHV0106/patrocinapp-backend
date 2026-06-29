package com.utp.patrocinapp.application.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarNegocioRequest extends RegistrarUsuarioRequest {

    @Pattern(regexp = "\\d{11}", message = "El RUC debe tener exactamente 11 dígitos.")
    private String ruc;

    @NotBlank(message = "La razón social es obligatoria.")
    private String razonSocial;

    private String correo;
    private String password;

}