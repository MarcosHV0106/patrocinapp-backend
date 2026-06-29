package com.utp.patrocinapp.infrastructure.persistence.mapper;

import com.utp.patrocinapp.domain.model.FondoGarantia;
import com.utp.patrocinapp.infrastructure.persistence.entity.FondoGarantiaEntity;

public class FondoGarantiaMapper {

    private FondoGarantiaMapper() {
    }

    public static FondoGarantia toDomain(FondoGarantiaEntity entity) {

        if (entity == null) {
            return null;
        }

        return new FondoGarantia(
                entity.getIdContrato(),
                entity.getMontoCongelado(),
                entity.getUltimaActualizacion()
        );

    }

    public static FondoGarantiaEntity toEntity(FondoGarantia domain) {

        if (domain == null) {
            return null;
        }

        return FondoGarantiaEntity.builder()
                .idContrato(domain.getIdContrato())
                .montoCongelado(domain.getMontoCongelado())
                .ultimaActualizacion(domain.getUltimaActualizacion())
                .build();

    }

}