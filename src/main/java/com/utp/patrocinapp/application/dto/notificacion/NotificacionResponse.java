package com.utp.patrocinapp.application.dto.notificacion;

import com.utp.patrocinapp.domain.enums.TipoNotificacion;
import com.utp.patrocinapp.domain.model.Notificacion;

import java.time.LocalDateTime;

public record NotificacionResponse(Long id, TipoNotificacion tipo, String mensaje,
                                   String entidadRelacionada, String idEntidad,
                                   LocalDateTime fecha, boolean leida, LocalDateTime fechaLectura) {
    public static NotificacionResponse from(Notificacion notificacion) {
        return new NotificacionResponse(notificacion.getId(), notificacion.getTipo(),
                notificacion.getMensaje(), notificacion.getEntidadRelacionada(),
                notificacion.getIdEntidad(), notificacion.getFecha(), notificacion.isLeida(),
                notificacion.getFechaLectura());
    }
}
