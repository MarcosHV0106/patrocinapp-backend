package com.utp.patrocinapp.application.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RegistrarUsuarioRequest {

    @Email(message = "El correo no tiene un formato válido.")
    @NotBlank(message = "El correo es obligatorio.")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String password;

}