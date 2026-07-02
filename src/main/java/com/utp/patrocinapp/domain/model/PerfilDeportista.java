package com.utp.patrocinapp.domain.model;

public class PerfilDeportista {

    private Integer idUsuario;
    private Usuario usuario;
    private String nombreCompleto;
    private String dni;
    private String disciplina;
    private String biografia;

    public PerfilDeportista() {
    }

    public PerfilDeportista(Integer idUsuario,
                            Usuario usuario,
                            String nombreCompleto,
                            String dni,
                            String disciplina,
                            String biografia) {

        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.disciplina = disciplina;
        this.biografia = biografia;
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

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
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

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public static PerfilDeportista crear(
            Usuario usuario,
            String nombreCompleto,
            String dni,
            String disciplina,
            String biografia) {

        PerfilDeportista perfil = new PerfilDeportista();

        perfil.setIdUsuario(usuario.getId());
        perfil.setUsuario(usuario);
        perfil.setNombreCompleto(nombreCompleto);
        perfil.setDni(dni);
        perfil.setDisciplina(disciplina);
        perfil.setBiografia(biografia);

        return perfil;
    }
}