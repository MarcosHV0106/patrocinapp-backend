package com.utp.patrocinapp.domain.ports.output;

import com.utp.patrocinapp.domain.model.FondoGarantia;

import java.util.Optional;

public interface FondoGarantiaRepositoryPort {

    FondoGarantia guardar(FondoGarantia fondoGarantia);

    Optional<FondoGarantia> buscarPorContrato(Integer idContrato);

}