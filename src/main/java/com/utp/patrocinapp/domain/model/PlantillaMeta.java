package com.utp.patrocinapp.domain.model;

import java.math.BigDecimal;

public class PlantillaMeta {

    private Integer id;
    private String nombreMeta;
    private String descripcionSugerida;
    private String tipoEntregable;
    private BigDecimal precioSugerido;

    public PlantillaMeta() {
    }

    public PlantillaMeta(
            Integer id,
            String nombreMeta,
            String descripcionSugerida,
            String tipoEntregable,
            BigDecimal precioSugerido) {

        this.id = id;
        this.nombreMeta = nombreMeta;
        this.descripcionSugerida = descripcionSugerida;
        this.tipoEntregable = tipoEntregable;
        this.precioSugerido = precioSugerido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreMeta() {
        return nombreMeta;
    }

    public void setNombreMeta(String nombreMeta) {
        this.nombreMeta = nombreMeta;
    }

    public String getDescripcionSugerida() {
        return descripcionSugerida;
    }

    public void setDescripcionSugerida(String descripcionSugerida) {
        this.descripcionSugerida = descripcionSugerida;
    }

    public String getTipoEntregable() {
        return tipoEntregable;
    }

    public void setTipoEntregable(String tipoEntregable) {
        this.tipoEntregable = tipoEntregable;
    }

    public BigDecimal getPrecioSugerido() {
        return precioSugerido;
    }

    public void setPrecioSugerido(BigDecimal precioSugerido) {
        this.precioSugerido = precioSugerido;
    }

}