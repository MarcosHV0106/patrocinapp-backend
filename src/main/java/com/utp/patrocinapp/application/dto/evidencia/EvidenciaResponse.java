package com.utp.patrocinapp.application.dto.evidencia;

import com.utp.patrocinapp.domain.enums.EstadoEvidencia;
import com.utp.patrocinapp.domain.model.Evidencia;

import java.time.LocalDateTime;

public record EvidenciaResponse(Long idEvidencia, Integer idMetaContrato, int numeroIntento,
                                String nombreOriginal, String tipoMime, long tamanioBytes,
                                String hashSha256, String comentarioDeportista,
                                EstadoEvidencia estado, String motivoRechazo,
                                LocalDateTime fechaCarga, LocalDateTime fechaRevision,
                                String urlArchivo) {
    public static EvidenciaResponse from(Evidencia evidencia) {
        return new EvidenciaResponse(evidencia.getIdEvidencia(), evidencia.getIdMetaContrato(),
                evidencia.getNumeroIntento(), evidencia.getNombreOriginal(), evidencia.getTipoMime(),
                evidencia.getTamanioBytes(), evidencia.getHashSha256(), evidencia.getComentarioDeportista(),
                evidencia.getEstado(), evidencia.getMotivoRechazo(), evidencia.getFechaCarga(),
                evidencia.getFechaRevision(), "/api/evidencias/" + evidencia.getIdEvidencia() + "/archivo");
    }
}
