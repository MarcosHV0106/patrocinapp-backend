package com.utp.patrocinapp.infrastructure.persistence.mapper;

import com.utp.patrocinapp.domain.model.Transaccion;
import com.utp.patrocinapp.infrastructure.persistence.entity.TransaccionEntity;

public class TransaccionMapper {

    private TransaccionMapper() {
    }

    public static Transaccion toDomain(TransaccionEntity entity) {

        if (entity == null) {
            return null;
        }

        return new Transaccion(
                entity.getId(),
                entity.getIdContrato(),
                entity.getIdMetaContrato(),
                entity.getMontoNeto(),
                entity.getComisionPlataforma(),
                entity.getFechaEjecucion()
        );

    }

    public static TransaccionEntity toEntity(Transaccion domain) {

        if (domain == null) {
            return null;
        }

        return TransaccionEntity.builder()
                .id(domain.getId())
                .idContrato(domain.getIdContrato())
                .idMetaContrato(domain.getIdMetaContrato())
                .montoNeto(domain.getMontoNeto())
                .comisionPlataforma(domain.getComisionPlataforma())
                .fechaEjecucion(domain.getFechaEjecucion())
                .build();

    }

}