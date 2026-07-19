package com.utp.patrocinapp.infrastructure.persistence.mapper;

import com.utp.patrocinapp.domain.model.Notificacion;
import com.utp.patrocinapp.infrastructure.persistence.entity.NotificacionEntity;

public final class NotificacionMapper {
    private NotificacionMapper() {
    }

    public static Notificacion toDomain(NotificacionEntity entity) {
        if (entity == null) return null;
        return new Notificacion(entity.getId(), entity.getIdUsuario(), entity.getTipo(), entity.getMensaje(),
                entity.getEntidadRelacionada(), entity.getIdEntidad(), entity.getFecha(), entity.isLeida(),
                entity.getFechaLectura());
    }

    public static NotificacionEntity toEntity(Notificacion domain) {
        if (domain == null) return null;
        return NotificacionEntity.builder()
                .id(domain.getId())
                .idUsuario(domain.getIdUsuario())
                .tipo(domain.getTipo())
                .mensaje(domain.getMensaje())
                .entidadRelacionada(domain.getEntidadRelacionada())
                .idEntidad(domain.getIdEntidad())
                .fecha(domain.getFecha())
                .leida(domain.isLeida())
                .fechaLectura(domain.getFechaLectura())
                .build();
    }
}
