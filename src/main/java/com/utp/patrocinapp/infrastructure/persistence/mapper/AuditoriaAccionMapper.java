package com.utp.patrocinapp.infrastructure.persistence.mapper;

import com.utp.patrocinapp.domain.model.AuditoriaAccion;
import com.utp.patrocinapp.infrastructure.persistence.entity.AuditoriaAccionEntity;

public final class AuditoriaAccionMapper {
    private AuditoriaAccionMapper() {
    }

    public static AuditoriaAccion toDomain(AuditoriaAccionEntity entity) {
        if (entity == null) return null;
        return new AuditoriaAccion(entity.getId(), entity.getIdUsuario(), entity.getAccion(), entity.getEntidad(),
                entity.getIdEntidad(), entity.getFecha(), entity.getResultado(), entity.getInformacionAdicional());
    }

    public static AuditoriaAccionEntity toEntity(AuditoriaAccion domain) {
        if (domain == null) return null;
        return AuditoriaAccionEntity.builder()
                .id(domain.getId())
                .idUsuario(domain.getIdUsuario())
                .accion(domain.getAccion())
                .entidad(domain.getEntidad())
                .idEntidad(domain.getIdEntidad())
                .fecha(domain.getFecha())
                .resultado(domain.getResultado())
                .informacionAdicional(domain.getInformacionAdicional())
                .build();
    }
}
