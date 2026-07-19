package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.evidencia.ArchivoEvidenciaResponse;

public interface DescargarEvidenciaInputPort {
    ArchivoEvidenciaResponse ejecutar(Long idEvidencia);
}
