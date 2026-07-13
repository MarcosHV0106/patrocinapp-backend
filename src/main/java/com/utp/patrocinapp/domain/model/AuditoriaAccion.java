package com.utp.patrocinapp.domain.model;

import java.time.LocalDateTime;

public class AuditoriaAccion {
    private Long id;
    private Integer idUsuario;
    private String accion;
    private String entidad;
    private String idEntidad;
    private LocalDateTime fecha;
    private String resultado;
    private String informacionAdicional;

    public AuditoriaAccion() {
    }

    public AuditoriaAccion(Long id, Integer idUsuario, String accion, String entidad,
                           String idEntidad, LocalDateTime fecha, String resultado,
                           String informacionAdicional) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.accion = accion;
        this.entidad = entidad;
        this.idEntidad = idEntidad;
        this.fecha = fecha;
        this.resultado = resultado;
        this.informacionAdicional = informacionAdicional;
    }

    public static AuditoriaAccion registrar(Integer idUsuario, String accion, String entidad,
                                             Object idEntidad, String resultado, String informacionAdicional) {
        return new AuditoriaAccion(null, idUsuario, accion, entidad, String.valueOf(idEntidad),
                LocalDateTime.now(), resultado, informacionAdicional);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
    public String getEntidad() { return entidad; }
    public void setEntidad(String entidad) { this.entidad = entidad; }
    public String getIdEntidad() { return idEntidad; }
    public void setIdEntidad(String idEntidad) { this.idEntidad = idEntidad; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    public String getInformacionAdicional() { return informacionAdicional; }
    public void setInformacionAdicional(String informacionAdicional) { this.informacionAdicional = informacionAdicional; }
}
