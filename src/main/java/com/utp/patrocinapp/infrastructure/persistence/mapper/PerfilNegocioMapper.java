package com.utp.patrocinapp.infrastructure.persistence.mapper;

import com.utp.patrocinapp.domain.model.PerfilNegocio;
import com.utp.patrocinapp.infrastructure.persistence.entity.PerfilNegocioEntity;

public class PerfilNegocioMapper {

    private PerfilNegocioMapper() {
    }

    public static PerfilNegocio toDomain(PerfilNegocioEntity entity) {

        if (entity == null) {
            return null;
        }

        return new PerfilNegocio(
                entity.getIdUsuario(),
                null,
                entity.getRuc(),
                entity.getRazonSocial()
        );

    }

    public static PerfilNegocioEntity toEntity(PerfilNegocio domain) {

        if (domain == null) {
            return null;
        }

        return PerfilNegocioEntity.builder()
                .idUsuario(domain.getIdUsuario())
                .ruc(domain.getRuc())
                .razonSocial(domain.getRazonSocial())
                .build();

    }

}