package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.meta.AprobarMetaResponse;

public interface AprobarMetaInputPort {

    AprobarMetaResponse ejecutar(Integer idMeta);

}