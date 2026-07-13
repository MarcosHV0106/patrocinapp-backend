package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.infrastructure.persistence.entity.TransaccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionJpaRepository
        extends JpaRepository<TransaccionEntity, Integer> {
    boolean existsByIdMetaContrato(Integer idMetaContrato);
    java.util.List<TransaccionEntity> findByIdContratoOrderByFechaEjecucionDesc(Integer idContrato);
}
