package com.utp.patrocinapp.infrastructure.persistence.adapter;

import com.utp.patrocinapp.domain.model.AuditoriaAccion;
import com.utp.patrocinapp.domain.ports.output.AuditoriaRepositoryPort;
import com.utp.patrocinapp.infrastructure.persistence.mapper.AuditoriaAccionMapper;
import com.utp.patrocinapp.infrastructure.persistence.repository.AuditoriaAccionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuditoriaRepositoryAdapter implements AuditoriaRepositoryPort {
    private final AuditoriaAccionJpaRepository repository;

    @Override
    public AuditoriaAccion guardar(AuditoriaAccion auditoria) {
        return AuditoriaAccionMapper.toDomain(repository.save(AuditoriaAccionMapper.toEntity(auditoria)));
    }
}
