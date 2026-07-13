package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.infrastructure.persistence.entity.FondoGarantiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface FondoGarantiaJpaRepository
        extends JpaRepository<FondoGarantiaEntity, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select f from FondoGarantiaEntity f where f.idContrato = :id")
    Optional<FondoGarantiaEntity> findByIdForUpdate(@Param("id") Integer id);
}
