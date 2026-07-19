package com.utp.patrocinapp.infrastructure.persistence.mapper;

import com.utp.patrocinapp.domain.model.MetaContrato;
import com.utp.patrocinapp.infrastructure.persistence.entity.MetaContratoEntity;

public class MetaContratoMapper {

    private MetaContratoMapper() {
    }

    public static MetaContrato toDomain(MetaContratoEntity entity) {

        if (entity == null) {
            return null;
        }

        return new MetaContrato(
                entity.getIdMetaContrato(),
                entity.getIdContrato(),
                entity.getIdPlantilla(),
                entity.getDescripcionAcordada(),
                entity.getMontoDeportista(),
                entity.getMontoNegocio(),
                entity.getComentarioDeportista(),
                entity.getUrlEvidencia(),
                entity.getEstado(),
                entity.getFechaActualizacion(),
                entity.getVersion()
        );

    }

    public static MetaContratoEntity toEntity(MetaContrato domain) {

        if (domain == null) {
            return null;
        }

        return MetaContratoEntity.builder()
                .idMetaContrato(domain.getIdMetaContrato())
                .idContrato(domain.getIdContrato())
                .idPlantilla(domain.getIdPlantilla())
                .descripcionAcordada(domain.getDescripcionAcordada())
                .montoDeportista(domain.getMontoDeportista())
                .montoNegocio(domain.getMontoNegocio())
                .comentarioDeportista(domain.getComentarioDeportista())
                .urlEvidencia(domain.getUrlEvidencia())
                .estado(domain.getEstado())
                .fechaActualizacion(domain.getFechaActualizacion())
                .version(domain.getVersion())
                .build();

    }

}
