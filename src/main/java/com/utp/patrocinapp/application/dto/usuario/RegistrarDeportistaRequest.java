package com.utp.patrocinapp.application.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarDeportistaRequest {

    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "El correo no es válido.")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String password;

    @NotBlank(message = "El DNI es obligatorio.")
    private String dni;

    @NotBlank(message = "La disciplina es obligatoria.")
    private String disciplina;

}