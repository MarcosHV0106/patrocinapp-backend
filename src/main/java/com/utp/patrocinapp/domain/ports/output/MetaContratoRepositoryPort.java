package com.utp.patrocinapp.domain.ports.output;

import com.utp.patrocinapp.domain.model.MetaContrato;

import java.util.List;
import java.util.Optional;

public interface MetaContratoRepositoryPort {

    MetaContrato guardar(MetaContrato metaContrato);

    Optional<MetaContrato> buscarPorId(Integer id);

    List<MetaContrato> listarPorContrato(Integer idContrato);

}