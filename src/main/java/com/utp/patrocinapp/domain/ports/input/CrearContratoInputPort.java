package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.contrato.CrearContratoRequest;
import com.utp.patrocinapp.application.dto.contrato.CrearContratoResponse;

public interface CrearContratoInputPort {

    CrearContratoResponse ejecutar(CrearContratoRequest request);

}