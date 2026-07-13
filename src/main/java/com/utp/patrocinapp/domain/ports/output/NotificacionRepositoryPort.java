package com.utp.patrocinapp.domain.ports.output;

import com.utp.patrocinapp.domain.model.Notificacion;

import java.util.List;
import java.util.Optional;

public interface NotificacionRepositoryPort {
    Notificacion guardar(Notificacion notificacion);
    Optional<Notificacion> buscarPorId(Long id);
    List<Notificacion> listarPorUsuario(Integer idUsuario);
}
