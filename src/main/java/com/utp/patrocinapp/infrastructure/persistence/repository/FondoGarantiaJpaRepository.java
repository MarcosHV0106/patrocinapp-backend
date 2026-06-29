package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.infrastructure.persistence.entity.FondoGarantiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FondoGarantiaJpaRepository
        extends JpaRepository<FondoGarantiaEntity, Integer> {
}