package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.evidencia.EvidenciaResponse;

import java.util.List;

public interface ConsultarEvidenciasInputPort {
    List<EvidenciaResponse> listarPorMeta(Integer idMeta);
    EvidenciaResponse consultarActual(Integer idMeta);
    EvidenciaResponse consultarPorId(Long idEvidencia);
}
