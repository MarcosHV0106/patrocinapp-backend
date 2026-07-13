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
                entity.getMontoInicial(),
                entity.getMontoCongelado(),
                entity.getMontoLiberado(),
                entity.getUltimaActualizacion(),
                entity.getVersion()
        );

    }

    public static FondoGarantiaEntity toEntity(FondoGarantia domain) {

        if (domain == null) {
            return null;
        }

        return FondoGarantiaEntity.builder()
                .idContrato(domain.getIdContrato())
                .montoInicial(domain.getMontoInicial())
                .montoCongelado(domain.getMontoCongelado())
                .montoLiberado(domain.getMontoLiberado())
                .ultimaActualizacion(domain.getUltimaActualizacion())
                .version(domain.getVersion())
                .build();

    }

}
