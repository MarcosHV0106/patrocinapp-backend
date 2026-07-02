package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.infrastructure.persistence.entity.ContratoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratoJpaRepository extends JpaRepository<ContratoEntity, Integer> {

    List<ContratoEntity> findByIdNegocioOrderByFechaCreacionDesc(Integer idNegocio);

    List<ContratoEntity> findByIdDeportistaOrderByFechaCreacionDesc(Integer idDeportista);

}