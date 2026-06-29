package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.infrastructure.persistence.entity.PerfilNegocioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilNegocioJpaRepository extends JpaRepository<PerfilNegocioEntity, Integer> {
}