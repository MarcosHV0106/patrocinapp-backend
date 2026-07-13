package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.infrastructure.persistence.entity.NotificacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionJpaRepository extends JpaRepository<NotificacionEntity, Long> {
    List<NotificacionEntity> findByIdUsuarioOrderByFechaDesc(Integer idUsuario);
}
