package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.infrastructure.persistence.entity.ContratoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoJpaRepository extends JpaRepository<ContratoEntity, Integer> {
}