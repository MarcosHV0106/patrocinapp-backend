package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.evidencia.EvidenciaResponse;

public interface RechazarEvidenciaInputPort {
    EvidenciaResponse ejecutar(Long idEvidencia, String motivo);
}
