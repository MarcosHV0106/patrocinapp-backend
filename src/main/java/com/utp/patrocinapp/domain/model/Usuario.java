package com.utp.patrocinapp.domain.model;
import com.utp.patrocinapp.domain.enums.Rol;

import java.time.LocalDateTime;

public class Usuario {

    private Integer id;
    private String correo;
    private String passwordHash;
    private Rol rol;
    private LocalDateTime fechaRegistro;

    public Usuario() {
    }

    public Usuario(Integer id,
                   String correo,
                   String passwordHash,
                   Rol rol,
                   LocalDateTime fechaRegistro) {

        this.id = id;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public static Usuario crear(
            String correo,
            String passwordHash,
            Rol rol) {

        Usuario usuario = new Usuario();

        usuario.setCorreo(correo);
        usuario.setPasswordHash(passwordHash);
        usuario.setRol(rol);
        usuario.setFechaRegistro(LocalDateTime.now());

        return usuario;
    }

    public boolean esNegocio() {
        return rol == Rol.NEGOCIO;
    }

    public boolean esDeportista() {
        return rol == Rol.DEPORTISTA;
    }

}