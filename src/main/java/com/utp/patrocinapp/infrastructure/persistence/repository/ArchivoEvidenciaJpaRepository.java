package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.infrastructure.persistence.entity.ArchivoEvidenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivoEvidenciaJpaRepository extends JpaRepository<ArchivoEvidenciaEntity, Long> {
}
