package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.contrato.ContratoDetalleResponse;

import java.util.List;

public interface ListarContratosInputPort {

    List<ContratoDetalleResponse> listarPorNegocio(Integer idNegocio);

    List<ContratoDetalleResponse> listarPorDeportista(Integer idDeportista);

}