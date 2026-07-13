package com.utp.patrocinapp.infrastructure.persistence.adapter;

import com.utp.patrocinapp.domain.enums.EstadoEvidencia;
import com.utp.patrocinapp.domain.model.Evidencia;
import com.utp.patrocinapp.domain.ports.output.EvidenciaRepositoryPort;
import com.utp.patrocinapp.infrastructure.persistence.mapper.EvidenciaMapper;
import com.utp.patrocinapp.infrastructure.persistence.repository.EvidenciaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EvidenciaRepositoryAdapter implements EvidenciaRepositoryPort {
    private final EvidenciaJpaRepository repository;

    @Override
    public Evidencia guardar(Evidencia evidencia) {
        return EvidenciaMapper.toDomain(repository.save(EvidenciaMapper.toEntity(evidencia)));
    }

    @Override
    public Optional<Evidencia> buscarPorId(Long idEvidencia) {
        return repository.findById(idEvidencia).map(EvidenciaMapper::toDomain);
    }

    @Override
    public Optional<Evidencia> buscarPorIdParaActualizar(Long idEvidencia) {
        return repository.findByIdForUpdate(idEvidencia).map(EvidenciaMapper::toDomain);
    }

    @Override
    public List<Evidencia> listarPorMeta(Integer idMetaContrato) {
        return repository.findByIdMetaContratoOrderByNumeroIntentoDesc(idMetaContrato)
                .stream().map(EvidenciaMapper::toDomain).toList();
    }

    @Override
    public Optional<Evidencia> buscarActualPorMeta(Integer idMetaContrato) {
        return repository.findFirstByIdMetaContratoAndEstadoOrderByNumeroIntentoDesc(
                idMetaContrato, EstadoEvidencia.EN_REVISION).map(EvidenciaMapper::toDomain);
    }

    @Override
    public long contarPorMeta(Integer idMetaContrato) {
        return repository.countByIdMetaContrato(idMetaContrato);
    }

    @Override
    public boolean existeHashParaMeta(Integer idMetaContrato, String hashSha256) {
        return repository.existsByIdMetaContratoAndHashSha256(idMetaContrato, hashSha256);
    }

    @Override
    public boolean existePorMetaYEstado(Integer idMetaContrato, EstadoEvidencia estado) {
        return repository.existsByIdMetaContratoAndEstado(idMetaContrato, estado);
    }
}
