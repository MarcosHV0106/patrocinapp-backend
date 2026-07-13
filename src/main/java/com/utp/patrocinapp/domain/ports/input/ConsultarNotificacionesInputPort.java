package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.notificacion.NotificacionResponse;

import java.util.List;

public interface ConsultarNotificacionesInputPort {
    List<NotificacionResponse> listar();
    NotificacionResponse marcarLeida(Long idNotificacion);
}
