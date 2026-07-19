package com.utp.patrocinapp.infrastructure.persistence.adapter;

import com.utp.patrocinapp.domain.model.Notificacion;
import com.utp.patrocinapp.domain.ports.output.NotificacionRepositoryPort;
import com.utp.patrocinapp.infrastructure.persistence.mapper.NotificacionMapper;
import com.utp.patrocinapp.infrastructure.persistence.repository.NotificacionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificacionRepositoryAdapter implements NotificacionRepositoryPort {
    private final NotificacionJpaRepository repository;

    @Override
    public Notificacion guardar(Notificacion notificacion) {
        return NotificacionMapper.toDomain(repository.save(NotificacionMapper.toEntity(notificacion)));
    }

    @Override
    public Optional<Notificacion> buscarPorId(Long id) {
        return repository.findById(id).map(NotificacionMapper::toDomain);
    }

    @Override
    public List<Notificacion> listarPorUsuario(Integer idUsuario) {
        return repository.findByIdUsuarioOrderByFechaDesc(idUsuario).stream()
                .map(NotificacionMapper::toDomain).toList();
    }
}
