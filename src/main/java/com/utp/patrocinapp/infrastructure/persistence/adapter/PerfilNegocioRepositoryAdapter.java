package com.utp.patrocinapp.infrastructure.persistence.adapter;

import com.utp.patrocinapp.domain.model.PerfilNegocio;
import com.utp.patrocinapp.domain.ports.output.PerfilNegocioRepositoryPort;
import com.utp.patrocinapp.infrastructure.persistence.mapper.PerfilNegocioMapper;
import com.utp.patrocinapp.infrastructure.persistence.repository.PerfilNegocioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PerfilNegocioRepositoryAdapter implements PerfilNegocioRepositoryPort {

    private final PerfilNegocioJpaRepository repository;

    @Override
    public PerfilNegocio guardar(PerfilNegocio domain) {

        return PerfilNegocioMapper.toDomain(
                repository.save(
                        PerfilNegocioMapper.toEntity(domain)
                )
        );

    }

    @Override
    public Optional<PerfilNegocio> buscarPorId(Integer idUsuario) {

        return repository.findById(idUsuario)
                .map(PerfilNegocioMapper::toDomain);

    }

}