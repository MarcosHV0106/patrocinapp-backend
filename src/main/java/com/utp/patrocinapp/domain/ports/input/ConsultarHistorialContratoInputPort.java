package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.contrato.HistorialContratoResponse;

public interface ConsultarHistorialContratoInputPort {
    HistorialContratoResponse ejecutar(Integer idContrato);
}
