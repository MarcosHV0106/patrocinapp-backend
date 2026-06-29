package com.utp.patrocinapp.domain.model;

public class PerfilDeportista {

    private Integer idUsuario;
    private Usuario usuario;
    private String dni;
    private String disciplina;

    public PerfilDeportista() {
    }

    public PerfilDeportista(Integer idUsuario,
                            Usuario usuario,
                            String dni,
                            String disciplina) {

        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.dni = dni;
        this.disciplina = disciplina;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public static PerfilDeportista crear(
            Usuario usuario,
            String dni,
            String disciplina) {

        PerfilDeportista perfil = new PerfilDeportista();

        perfil.setIdUsuario(usuario.getId());
        perfil.setUsuario(usuario);
        perfil.setDni(dni);
        perfil.setDisciplina(disciplina);

        return perfil;
    }
}