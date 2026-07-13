package com.utp.patrocinapp.infrastructure.persistence.adapter;

import com.utp.patrocinapp.domain.model.MetaContrato;
import com.utp.patrocinapp.domain.ports.output.MetaContratoRepositoryPort;
import com.utp.patrocinapp.infrastructure.persistence.mapper.MetaContratoMapper;
import com.utp.patrocinapp.infrastructure.persistence.repository.MetaContratoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MetaContratoRepositoryAdapter implements MetaContratoRepositoryPort {

    private final MetaContratoJpaRepository repository;

    @Override
    public MetaContrato guardar(MetaContrato metaContrato) {

        return MetaContratoMapper.toDomain(
                repository.save(
                        MetaContratoMapper.toEntity(metaContrato)
                )
        );

    }

    @Override
    public Optional<MetaContrato> buscarPorId(Integer id) {

        return repository.findById(id)
                .map(MetaContratoMapper::toDomain);

    }

    @Override
    public Optional<MetaContrato> buscarPorIdParaActualizar(Integer id) {
        return repository.findByIdForUpdate(id).map(MetaContratoMapper::toDomain);
    }

    @Override
    public List<MetaContrato> listarPorContrato(Integer idContrato) {

        return repository.findByIdContratoOrderByIdMetaContratoAsc(idContrato)
                .stream()
                .map(MetaContratoMapper::toDomain)
                .toList();

    }

    @Override
    public long contarNoPagadasPorContrato(Integer idContrato) {
        return repository.countByIdContratoAndEstadoNot(idContrato, com.utp.patrocinapp.domain.enums.EstadoMeta.PAGADA);
    }

}
