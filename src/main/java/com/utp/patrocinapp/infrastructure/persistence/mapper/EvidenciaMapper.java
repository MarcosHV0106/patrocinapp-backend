package com.utp.patrocinapp.infrastructure.persistence.mapper;

import com.utp.patrocinapp.domain.model.Evidencia;
import com.utp.patrocinapp.infrastructure.persistence.entity.EvidenciaEntity;

public final class EvidenciaMapper {
    private EvidenciaMapper() {
    }

    public static Evidencia toDomain(EvidenciaEntity entity) {
        if (entity == null) return null;
        return new Evidencia(entity.getIdEvidencia(), entity.getIdMetaContrato(), entity.getNumeroIntento(),
                entity.getNombreOriginal(), entity.getTipoMime(), entity.getTamanioBytes(),
                entity.getHashSha256(), entity.getComentarioDeportista(), entity.getEstado(),
                entity.getMotivoRechazo(), entity.getFechaCarga(), entity.getFechaRevision(),
                entity.getIdUsuarioRevisor(), entity.getFechaActualizacion(), entity.getVersion());
    }

    public static EvidenciaEntity toEntity(Evidencia domain) {
        if (domain == null) return null;
        return EvidenciaEntity.builder()
                .idEvidencia(domain.getIdEvidencia())
                .idMetaContrato(domain.getIdMetaContrato())
                .numeroIntento(domain.getNumeroIntento())
                .nombreOriginal(domain.getNombreOriginal())
                .tipoMime(domain.getTipoMime())
                .tamanioBytes(domain.getTamanioBytes())
                .hashSha256(domain.getHashSha256())
                .comentarioDeportista(domain.getComentarioDeportista())
                .estado(domain.getEstado())
                .motivoRechazo(domain.getMotivoRechazo())
                .fechaCarga(domain.getFechaCarga())
                .fechaRevision(domain.getFechaRevision())
                .idUsuarioRevisor(domain.getIdUsuarioRevisor())
                .fechaActualizacion(domain.getFechaActualizacion())
                .version(domain.getVersion())
                .build();
    }
}
