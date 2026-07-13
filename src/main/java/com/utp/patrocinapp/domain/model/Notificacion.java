package com.utp.patrocinapp.domain.model;

import com.utp.patrocinapp.domain.enums.TipoNotificacion;

import java.time.LocalDateTime;

public class Notificacion {
    private Long id;
    private Integer idUsuario;
    private TipoNotificacion tipo;
    private String mensaje;
    private String entidadRelacionada;
    private String idEntidad;
    private LocalDateTime fecha;
    private boolean leida;
    private LocalDateTime fechaLectura;

    public Notificacion() {
    }

    public Notificacion(Long id, Integer idUsuario, TipoNotificacion tipo, String mensaje,
                        String entidadRelacionada, String idEntidad, LocalDateTime fecha,
                        boolean leida, LocalDateTime fechaLectura) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.entidadRelacionada = entidadRelacionada;
        this.idEntidad = idEntidad;
        this.fecha = fecha;
        this.leida = leida;
        this.fechaLectura = fechaLectura;
    }

    public static Notificacion crear(Integer idUsuario, TipoNotificacion tipo, String mensaje,
                                      String entidadRelacionada, Object idEntidad) {
        return new Notificacion(null, idUsuario, tipo, mensaje, entidadRelacionada,
                String.valueOf(idEntidad), LocalDateTime.now(), false, null);
    }

    public void marcarLeida() {
        if (!leida) {
            leida = true;
            fechaLectura = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public TipoNotificacion getTipo() { return tipo; }
    public void setTipo(TipoNotificacion tipo) { this.tipo = tipo; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getEntidadRelacionada() { return entidadRelacionada; }
    public void setEntidadRelacionada(String entidadRelacionada) { this.entidadRelacionada = entidadRelacionada; }
    public String getIdEntidad() { return idEntidad; }
    public void setIdEntidad(String idEntidad) { this.idEntidad = idEntidad; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public boolean isLeida() { return leida; }
    public void setLeida(boolean leida) { this.leida = leida; }
    public LocalDateTime getFechaLectura() { return fechaLectura; }
    public void setFechaLectura(LocalDateTime fechaLectura) { this.fechaLectura = fechaLectura; }
}
