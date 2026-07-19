package com.utp.patrocinapp.domain.ports.output;

import com.utp.patrocinapp.domain.model.Transaccion;

public interface TransaccionRepositoryPort {

    Transaccion guardar(Transaccion transaccion);

    boolean existePorMeta(Integer idMetaContrato);

    java.util.List<Transaccion> listarPorContrato(Integer idContrato);

}
