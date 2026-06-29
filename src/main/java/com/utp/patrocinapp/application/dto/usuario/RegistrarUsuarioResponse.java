package com.utp.patrocinapp.application.dto.usuario;

import com.utp.patrocinapp.domain.enums.Rol;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegistrarUsuarioResponse {

    private Integer id;

    private String correo;

    private Rol rol;

}