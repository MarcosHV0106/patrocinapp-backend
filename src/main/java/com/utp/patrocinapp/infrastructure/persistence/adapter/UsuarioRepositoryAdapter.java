package com.utp.patrocinapp.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.utp.patrocinapp.domain.model.Usuario;
import com.utp.patrocinapp.domain.ports.output.UsuarioRepositoryPort;
import com.utp.patrocinapp.infrastructure.persistence.entity.UsuarioEntity;
import com.utp.patrocinapp.infrastructure.persistence.mapper.UsuarioMapper;
import com.utp.patrocinapp.infrastructure.persistence.repository.UsuarioJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository repository;

    @Override
    public Usuario guardar(Usuario usuario) {

        UsuarioEntity entity = UsuarioMapper.toEntity(usuario);

        UsuarioEntity saved = repository.save(entity);

        return UsuarioMapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {

        return repository
                .findById(id)
                .map(UsuarioMapper::toDomain);

    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {

        return repository.findByCorreo(correo)
                .map(UsuarioMapper::toDomain);

    }

    @Override
    public boolean existePorCorreo(String correo) {

        return repository.existsByCorreo(correo);

    }

}