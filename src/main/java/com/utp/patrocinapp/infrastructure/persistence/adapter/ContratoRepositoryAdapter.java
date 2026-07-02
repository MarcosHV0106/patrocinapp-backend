package com.utp.patrocinapp.infrastructure.persistence.adapter;

import com.utp.patrocinapp.domain.model.Contrato;
import com.utp.patrocinapp.domain.ports.output.ContratoRepositoryPort;
import com.utp.patrocinapp.infrastructure.persistence.mapper.ContratoMapper;
import com.utp.patrocinapp.infrastructure.persistence.repository.ContratoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Contrato> buscarPorId(Integer idContrato) {

        return repository.findById(idContrato)
                .map(ContratoMapper::toDomain);

    }

    @Override
    public List<Contrato> buscarPorNegocio(Integer idNegocio) {

        return repository.findByIdNegocioOrderByFechaCreacionDesc(idNegocio)
                .stream()
                .map(ContratoMapper::toDomain)
                .toList();

    }

    @Override
    public List<Contrato> buscarPorDeportista(Integer idDeportista) {

        return repository.findByIdDeportistaOrderByFechaCreacionDesc(idDeportista)
                .stream()
                .map(ContratoMapper::toDomain)
                .toList();

    }

}