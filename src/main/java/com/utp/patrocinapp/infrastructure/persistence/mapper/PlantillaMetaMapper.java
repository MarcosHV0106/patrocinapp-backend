package com.utp.patrocinapp.infrastructure.persistence.mapper;

import com.utp.patrocinapp.domain.model.PlantillaMeta;
import com.utp.patrocinapp.infrastructure.persistence.entity.PlantillaMetaEntity;

public class PlantillaMetaMapper {

    private PlantillaMetaMapper() {
    }

    public static PlantillaMeta toDomain(PlantillaMetaEntity entity) {

        if (entity == null) {
            return null;
        }

        return new PlantillaMeta(
                entity.getId(),
                entity.getNombreMeta(),
                entity.getDescripcionSugerida(),
                entity.getTipoEntregable(),
                entity.getPrecioSugerido()
        );

    }

}