package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.meta.RegistrarEvidenciaRequest;

public interface RegistrarEvidenciaInputPort {

    void ejecutar(
            Integer idMeta,
            RegistrarEvidenciaRequest request
    );

}