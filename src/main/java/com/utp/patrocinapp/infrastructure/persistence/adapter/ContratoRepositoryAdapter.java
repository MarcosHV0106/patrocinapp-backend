package com.utp.patrocinapp.infrastructure.persistence.adapter;

import com.utp.patrocinapp.domain.model.Contrato;
import com.utp.patrocinapp.domain.ports.output.ContratoRepositoryPort;
import com.utp.patrocinapp.infrastructure.persistence.mapper.ContratoMapper;
import com.utp.patrocinapp.infrastructure.persistence.repository.ContratoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContratoRepositoryAdapter implements ContratoRepositoryPort {

    private final ContratoJpaRepository repository;

    @Override
    public Contrato guardar(Contrato contrato) {

        return ContratoMapper.toDomain(
                repository.save(
                        ContratoMapper.toEntity(contrato)
                )
        );

    }

}