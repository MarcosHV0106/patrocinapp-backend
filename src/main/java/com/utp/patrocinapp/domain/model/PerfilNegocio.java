package com.utp.patrocinapp.domain.model;

public class PerfilNegocio {

    private Integer idUsuario;
    private Usuario usuario;
    private String ruc;
    private String razonSocial;

    public PerfilNegocio() {
    }

    public PerfilNegocio(Integer idUsuario,
                         Usuario usuario,
                         String ruc,
                         String razonSocial) {

        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.ruc = ruc;
        this.razonSocial = razonSocial;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public static PerfilNegocio crear(
            Usuario usuario,
            String ruc,
            String razonSocial) {

        PerfilNegocio perfil = new PerfilNegocio();

        perfil.setIdUsuario(usuario.getId());
        perfil.setUsuario(usuario);
        perfil.setRuc(ruc);
        perfil.setRazonSocial(razonSocial);

        return perfil;
    }
}