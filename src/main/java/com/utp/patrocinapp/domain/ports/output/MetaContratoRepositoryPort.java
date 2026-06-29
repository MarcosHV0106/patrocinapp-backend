package com.utp.patrocinapp.domain.ports.output;

import com.utp.patrocinapp.domain.model.MetaContrato;

public interface MetaContratoRepositoryPort {

    MetaContrato guardar(MetaContrato metaContrato);

}