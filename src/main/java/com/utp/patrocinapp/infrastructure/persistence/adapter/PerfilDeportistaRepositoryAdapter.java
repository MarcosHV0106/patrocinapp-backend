package com.utp.patrocinapp.infrastructure.persistence.adapter;

import com.utp.patrocinapp.domain.model.PerfilDeportista;
import com.utp.patrocinapp.domain.ports.output.PerfilDeportistaRepositoryPort;
import com.utp.patrocinapp.infrastructure.persistence.mapper.PerfilDeportistaMapper;
import com.utp.patrocinapp.infrastructure.persistence.repository.PerfilDeportistaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PerfilDeportistaRepositoryAdapter implements PerfilDeportistaRepositoryPort {

    private final PerfilDeportistaJpaRepository repository;

    @Override
    public PerfilDeportista guardar(PerfilDeportista domain) {

        return PerfilDeportistaMapper.toDomain(
                repository.save(
                        PerfilDeportistaMapper.toEntity(domain)
                )
        );

    }

    @Override
    public Optional<PerfilDeportista> buscarPorId(Integer idUsuario) {

        return repository.findById(idUsuario)
                .map(PerfilDeportistaMapper::toDomain);

    }

}