package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.infrastructure.persistence.entity.PlantillaMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantillaMetaJpaRepository
        extends JpaRepository<PlantillaMetaEntity, Integer> {

}