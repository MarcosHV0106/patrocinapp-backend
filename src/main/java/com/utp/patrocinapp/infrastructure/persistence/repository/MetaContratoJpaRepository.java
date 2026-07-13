package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.infrastructure.persistence.entity.MetaContratoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import com.utp.patrocinapp.domain.enums.EstadoMeta;

import java.util.List;

public interface MetaContratoJpaRepository extends JpaRepository<MetaContratoEntity, Integer> {

    List<MetaContratoEntity> findByIdContratoOrderByIdMetaContratoAsc(Integer idContrato);

    long countByIdContratoAndEstadoNot(Integer idContrato, EstadoMeta estado);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from MetaContratoEntity m where m.idMetaContrato = :id")
    java.util.Optional<MetaContratoEntity> findByIdForUpdate(@Param("id") Integer id);

}
