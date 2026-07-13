package com.utp.patrocinapp.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FondoGarantia {

    private Integer idContrato;
    private BigDecimal montoInicial;
    private BigDecimal montoCongelado;
    private BigDecimal montoLiberado;
    private LocalDateTime ultimaActualizacion;
    private Long version;

    public FondoGarantia() {
    }

    public FondoGarantia(
            Integer idContrato,
            BigDecimal montoInicial,
            BigDecimal montoCongelado,
            BigDecimal montoLiberado,
            LocalDateTime ultimaActualizacion,
            Long version) {

        this.idContrato = idContrato;
        this.montoInicial = montoInicial;
        this.montoCongelado = montoCongelado;
        this.montoLiberado = montoLiberado;
        this.ultimaActualizacion = ultimaActualizacion;
        this.version = version;
    }

    public static FondoGarantia crear(Integer idContrato,
                                      BigDecimal monto) {

        FondoGarantia fondo = new FondoGarantia();

        fondo.setIdContrato(idContrato);
        fondo.setMontoInicial(monto);
        fondo.setMontoCongelado(monto);
        fondo.setMontoLiberado(BigDecimal.ZERO);
        fondo.setUltimaActualizacion(LocalDateTime.now());
        fondo.setVersion(0L);

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

    public BigDecimal getMontoInicial() { return montoInicial; }
    public void setMontoInicial(BigDecimal montoInicial) { this.montoInicial = montoInicial; }
    public BigDecimal getMontoLiberado() { return montoLiberado; }
    public void setMontoLiberado(BigDecimal montoLiberado) { this.montoLiberado = montoLiberado; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public void liberar(BigDecimal monto) {
        if (monto == null || monto.signum() <= 0) {
            throw new IllegalArgumentException("El monto a liberar debe ser positivo.");
        }
        if (montoCongelado.compareTo(monto) < 0) {
            throw new IllegalStateException("El fondo no tiene saldo suficiente.");
        }
        montoCongelado = montoCongelado.subtract(monto);
        montoLiberado = montoLiberado.add(monto);
        ultimaActualizacion = LocalDateTime.now();
    }

    public LocalDateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

}
