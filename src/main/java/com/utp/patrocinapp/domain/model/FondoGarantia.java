package com.utp.patrocinapp.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FondoGarantia {

    private Integer idContrato;
    private BigDecimal montoCongelado;
    private LocalDateTime ultimaActualizacion;

    public FondoGarantia() {
    }

    public FondoGarantia(
            Integer idContrato,
            BigDecimal montoCongelado,
            LocalDateTime ultimaActualizacion) {

        this.idContrato = idContrato;
        this.montoCongelado = montoCongelado;
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public static FondoGarantia crear(Integer idContrato,
                                      BigDecimal monto) {

        FondoGarantia fondo = new FondoGarantia();

        fondo.setIdContrato(idContrato);
        fondo.setMontoCongelado(monto);
        fondo.setUltimaActualizacion(LocalDateTime.now());

        return fondo;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public BigDecimal getMontoCongelado() {
        return montoCongelado;
    }

    public void setMontoCongelado(BigDecimal montoCongelado) {
        this.montoCongelado = montoCongelado;
    }

    public LocalDateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

}