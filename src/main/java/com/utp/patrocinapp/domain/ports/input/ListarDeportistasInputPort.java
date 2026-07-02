package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.deportista.DeportistaCatalogoResponse;

import java.util.List;

public interface ListarDeportistasInputPort {

    List<DeportistaCatalogoResponse> ejecutar(String busqueda, String disciplina);

}