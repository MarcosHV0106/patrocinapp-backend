package com.utp.patrocinapp.application.dto.auth;

import com.utp.patrocinapp.domain.enums.Rol;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private Integer id;

    private String correo;

    private Rol rol;

    private String token;

}