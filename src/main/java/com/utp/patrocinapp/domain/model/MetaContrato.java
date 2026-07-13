package com.utp.patrocinapp.domain.model;

import com.utp.patrocinapp.domain.enums.EstadoMeta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MetaContrato {

    private Integer idMetaContrato;
    private Integer idContrato;
    private Integer idPlantilla;

    private String descripcionAcordada;

    private BigDecimal montoDeportista;
    private BigDecimal montoNegocio;

    private String comentarioDeportista;
    private String urlEvidencia;

    private EstadoMeta estado;

    private LocalDateTime fechaActualizacion;
    private Long version;

    public MetaContrato() {
    }

    public MetaContrato(
            Integer idMetaContrato,
            Integer idContrato,
            Integer idPlantilla,
            String descripcionAcordada,
            BigDecimal montoDeportista,
            BigDecimal montoNegocio,
            String comentarioDeportista,
            String urlEvidencia,
            EstadoMeta estado,
            LocalDateTime fechaActualizacion,
            Long version) {

        this.idMetaContrato = idMetaContrato;
        this.idContrato = idContrato;
        this.idPlantilla = idPlantilla;
        this.descripcionAcordada = descripcionAcordada;
        this.montoDeportista = montoDeportista;
        this.montoNegocio = montoNegocio;
        this.comentarioDeportista = comentarioDeportista;
        this.urlEvidencia = urlEvidencia;
        this.estado = estado;
        this.fechaActualizacion = fechaActualizacion;
        this.version = version;
    }

    public static MetaContrato crear(
            Integer idPlantilla,
            String descripcionAcordada,
            BigDecimal montoDeportista,
            BigDecimal montoNegocio,
            String comentarioDeportista) {

        MetaContrato meta = new MetaContrato();

        meta.setIdPlantilla(idPlantilla);
        meta.setDescripcionAcordada(descripcionAcordada);
        meta.setMontoDeportista(montoDeportista);
        meta.setMontoNegocio(montoNegocio);
        meta.setComentarioDeportista(comentarioDeportista);
        meta.setEstado(EstadoMeta.PENDIENTE);
        meta.setFechaActualizacion(LocalDateTime.now());
        meta.setVersion(null);

        return meta;
    }

    public Integer getIdMetaContrato() {
        return idMetaContrato;
    }

    public void setIdMetaContrato(Integer idMetaContrato) {
        this.idMetaContrato = idMetaContrato;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public String getDescripcionAcordada() {
        return descripcionAcordada;
    }

    public void setDescripcionAcordada(String descripcionAcordada) {
        this.descripcionAcordada = descripcionAcordada;
    }

    public BigDecimal getMontoDeportista() {
        return montoDeportista;
    }

    public void setMontoDeportista(BigDecimal montoDeportista) {
        this.montoDeportista = montoDeportista;
    }

    public BigDecimal getMontoNegocio() {
        return montoNegocio;
    }

    public void setMontoNegocio(BigDecimal montoNegocio) {
        this.montoNegocio = montoNegocio;
    }

    public String getComentarioDeportista() {
        return comentarioDeportista;
    }

    public void setComentarioDeportista(String comentarioDeportista) {
        this.comentarioDeportista = comentarioDeportista;
    }

    public String getUrlEvidencia() {
        return urlEvidencia;
    }

    public void setUrlEvidencia(String urlEvidencia) {
        this.urlEvidencia = urlEvidencia;
    }

    public EstadoMeta getEstado() {
        return estado;
    }

    public void setEstado(EstadoMeta estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public boolean puedeRecibirEvidencia() {
        return estado == EstadoMeta.PENDIENTE || estado == EstadoMeta.RECHAZADA;
    }

    public void marcarEnRevision(String comentario) {
        if (!puedeRecibirEvidencia()) {
            throw new IllegalStateException("La meta no admite una nueva evidencia en su estado actual.");
        }
        this.comentarioDeportista = comentario;
        this.estado = EstadoMeta.EN_REVISION;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void marcarRechazada() {
        if (estado != EstadoMeta.EN_REVISION) {
            throw new IllegalStateException("Solo una meta en revisión puede rechazarse.");
        }
        this.estado = EstadoMeta.RECHAZADA;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void marcarAprobada() {
        if (estado != EstadoMeta.EN_REVISION) {
            throw new IllegalStateException("Solo una meta en revisión puede aprobarse.");
        }
        this.estado = EstadoMeta.APROBADA;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void marcarPagada() {
        if (estado != EstadoMeta.APROBADA) {
            throw new IllegalStateException("Solo una meta aprobada puede pagarse.");
        }
        this.estado = EstadoMeta.PAGADA;
        this.fechaActualizacion = LocalDateTime.now();
    }
}
