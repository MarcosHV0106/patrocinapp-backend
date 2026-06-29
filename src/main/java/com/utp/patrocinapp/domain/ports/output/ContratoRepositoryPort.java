package com.utp.patrocinapp.domain.ports.output;

import com.utp.patrocinapp.domain.model.Contrato;

public interface ContratoRepositoryPort {

    Contrato guardar(Contrato contrato);

}