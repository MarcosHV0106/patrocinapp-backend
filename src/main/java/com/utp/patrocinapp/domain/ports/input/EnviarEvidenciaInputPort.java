package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.evidencia.ArchivoEvidenciaCommand;
import com.utp.patrocinapp.application.dto.evidencia.EvidenciaResponse;

public interface EnviarEvidenciaInputPort {
    EvidenciaResponse ejecutar(Integer idMeta, ArchivoEvidenciaCommand archivo);
}
