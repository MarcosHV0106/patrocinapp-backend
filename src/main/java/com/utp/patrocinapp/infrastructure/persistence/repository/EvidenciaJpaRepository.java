package com.utp.patrocinapp.infrastructure.persistence.repository;

import com.utp.patrocinapp.domain.enums.EstadoEvidencia;
import com.utp.patrocinapp.infrastructure.persistence.entity.EvidenciaEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EvidenciaJpaRepository extends JpaRepository<EvidenciaEntity, Long> {
    List<EvidenciaEntity> findByIdMetaContratoOrderByNumeroIntentoDesc(Integer idMetaContrato);
    Optional<EvidenciaEntity> findFirstByIdMetaContratoAndEstadoOrderByNumeroIntentoDesc(
            Integer idMetaContrato, EstadoEvidencia estado);
    long countByIdMetaContrato(Integer idMetaContrato);
    boolean existsByIdMetaContratoAndHashSha256(Integer idMetaContrato, String hashSha256);
    boolean existsByIdMetaContratoAndEstado(Integer idMetaContrato, EstadoEvidencia estado);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from EvidenciaEntity e where e.idEvidencia = :id")
    Optional<EvidenciaEntity> findByIdForUpdate(@Param("id") Long id);
}
