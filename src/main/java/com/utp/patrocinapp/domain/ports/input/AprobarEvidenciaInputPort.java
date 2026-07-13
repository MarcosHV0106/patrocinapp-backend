package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.evidencia.AprobarEvidenciaResponse;

public interface AprobarEvidenciaInputPort {
    AprobarEvidenciaResponse ejecutar(Long idEvidencia);
}
