package com.utp.patrocinapp.domain.model;

import com.utp.patrocinapp.domain.enums.Rol;

public record UsuarioAutenticado(Integer id, String correo, Rol rol) {
    public boolean tieneRol(Rol esperado) {
        return rol == esperado;
    }
}
