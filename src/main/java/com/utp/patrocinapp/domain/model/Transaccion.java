package com.utp.patrocinapp.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaccion {

    private Integer id;
    private Integer idContrato;
    private Integer idMetaContrato;
    private BigDecimal montoNeto;
    private BigDecimal comisionPlataforma;
    LocalDateTime fechaEjecucion;

    public Transaccion() {}

    public Transaccion(Integer id, Integer idContrato, Integer idMetaContrato, BigDecimal montoNeto, BigDecimal comisionPlataforma, LocalDateTime fechaEjecucion) {
        this.id = id;
        this.idContrato = idContrato;
        this.idMetaContrato = idMetaContrato;
        this.montoNeto = montoNeto;
        this.comisionPlataforma = comisionPlataforma;
        this.fechaEjecucion = fechaEjecucion;
    }

    public static Transaccion crear(
            Integer idContrato,
            Integer idMetaContrato,
            BigDecimal montoNeto,
            BigDecimal comisionPlataforma) {

        Transaccion t = new Transaccion();

        t.setIdContrato(idContrato);
        t.setIdMetaContrato(idMetaContrato);
        t.setMontoNeto(montoNeto);
        t.setComisionPlataforma(comisionPlataforma);
        t.setFechaEjecucion(LocalDateTime.now());

        return t;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public Integer getIdMetaContrato() {
        return idMetaContrato;
    }

    public void setIdMetaContrato(Integer idMetaContrato) {
        this.idMetaContrato = idMetaContrato;
    }

    public BigDecimal getMontoNeto() {
        return montoNeto;
    }

    public void setMontoNeto(BigDecimal montoNeto) {
        this.montoNeto = montoNeto;
    }

    public BigDecimal getComisionPlataforma() {
        return comisionPlataforma;
    }

    public void setComisionPlataforma(BigDecimal comisionPlataforma) {
        this.comisionPlataforma = comisionPlataforma;
    }

    public LocalDateTime getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(LocalDateTime fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }
}
