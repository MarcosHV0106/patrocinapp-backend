package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.infrastructure.persistence.entity.PerfilDeportistaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilDeportistaJpaRepository extends JpaRepository<PerfilDeportistaEntity, Integer> {
}