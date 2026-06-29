package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.infrastructure.persistence.entity.MetaContratoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetaContratoJpaRepository
        extends JpaRepository<MetaContratoEntity, Integer> {
}