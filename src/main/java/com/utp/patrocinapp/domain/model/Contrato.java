package com.utp.patrocinapp.domain.model;

import com.utp.patrocinapp.domain.enums.EstadoContrato;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Contrato {

    private Integer idContrato;
    private Integer idNegocio;
    private Integer idDeportista;
    private BigDecimal montoTotal;
    private EstadoContrato estado;
    private LocalDateTime fechaCreacion;
    private Long version;

    public Contrato() {
    }

    public Contrato(
            Integer idContrato,
            Integer idNegocio,
            Integer idDeportista,
            BigDecimal montoTotal,
            EstadoContrato estado,
            LocalDateTime fechaCreacion,
            Long version) {

        this.idContrato = idContrato;
        this.idNegocio = idNegocio;
        this.idDeportista = idDeportista;
        this.montoTotal = montoTotal;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.version = version;
    }

    public static Contrato crear(
            Integer idNegocio,
            Integer idDeportista,
            BigDecimal montoTotal) {

        Contrato contrato = new Contrato();

        contrato.setIdNegocio(idNegocio);
        contrato.setIdDeportista(idDeportista);
        contrato.setMontoTotal(montoTotal);
        contrato.setEstado(EstadoContrato.ACTIVO);
        contrato.setFechaCreacion(LocalDateTime.now());
        contrato.setVersion(0L);

        return contrato;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public Integer getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(Integer idNegocio) {
        this.idNegocio = idNegocio;
    }

    public Integer getIdDeportista() {
        return idDeportista;
    }

    public void setIdDeportista(Integer idDeportista) {
        this.idDeportista = idDeportista;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public void finalizar() {
        this.estado = EstadoContrato.FINALIZADO;
    }

}
