package com.utp.patrocinapp.domain.ports.output;

import com.utp.patrocinapp.domain.model.Contrato;

import java.util.List;
import java.util.Optional;

public interface ContratoRepositoryPort {

    Contrato guardar(Contrato contrato);

    Optional<Contrato> buscarPorId(Integer idContrato);

    List<Contrato> buscarPorNegocio(Integer idNegocio);

    List<Contrato> buscarPorDeportista(Integer idDeportista);

}