package com.utp.patrocinapp.infrastructure.persistence.adapter;

import com.utp.patrocinapp.domain.model.FondoGarantia;
import com.utp.patrocinapp.domain.ports.output.FondoGarantiaRepositoryPort;
import com.utp.patrocinapp.infrastructure.persistence.mapper.FondoGarantiaMapper;
import com.utp.patrocinapp.infrastructure.persistence.repository.FondoGarantiaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FondoGarantiaRepositoryAdapter implements FondoGarantiaRepositoryPort {

    private final FondoGarantiaJpaRepository repository;

    @Override
    public FondoGarantia guardar(FondoGarantia fondoGarantia) {

        return FondoGarantiaMapper.toDomain(
                repository.save(
                        FondoGarantiaMapper.toEntity(fondoGarantia)
                )
        );

    }

    @Override
    public Optional<FondoGarantia> buscarPorContrato(Integer idContrato) {

        return repository.findById(idContrato)
                .map(FondoGarantiaMapper::toDomain);

    }

}