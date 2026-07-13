package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.infrastructure.persistence.entity.ContratoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

import java.util.List;

public interface ContratoJpaRepository extends JpaRepository<ContratoEntity, Integer> {

    List<ContratoEntity> findByIdNegocioOrderByFechaCreacionDesc(Integer idNegocio);

    List<ContratoEntity> findByIdDeportistaOrderByFechaCreacionDesc(Integer idDeportista);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from ContratoEntity c where c.idContrato = :id")
    java.util.Optional<ContratoEntity> findByIdForUpdate(@Param("id") Integer id);

}
