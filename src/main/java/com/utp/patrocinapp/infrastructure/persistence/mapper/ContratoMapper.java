package com.utp.patrocinapp.infrastructure.persistence.mapper;

import com.utp.patrocinapp.domain.model.Contrato;
import com.utp.patrocinapp.infrastructure.persistence.entity.ContratoEntity;

public class ContratoMapper {

    private ContratoMapper() {
    }

    public static Contrato toDomain(ContratoEntity entity) {

        if (entity == null) {
            return null;
        }

        return new Contrato(
                entity.getIdContrato(),
                entity.getIdNegocio(),
                entity.getIdDeportista(),
                entity.getMontoTotal(),
                entity.getEstado(),
                entity.getFechaCreacion()
        );

    }

    public static ContratoEntity toEntity(Contrato domain) {

        if (domain == null) {
            return null;
        }

        return ContratoEntity.builder()
                .idContrato(domain.getIdContrato())
                .idNegocio(domain.getIdNegocio())
                .idDeportista(domain.getIdDeportista())
                .montoTotal(domain.getMontoTotal())
                .estado(domain.getEstado())
                .fechaCreacion(domain.getFechaCreacion())
                .build();

    }

}