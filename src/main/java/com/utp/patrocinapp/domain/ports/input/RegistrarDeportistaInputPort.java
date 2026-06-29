package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.usuario.RegistrarDeportistaRequest;
import com.utp.patrocinapp.application.dto.usuario.RegistrarUsuarioResponse;

public interface RegistrarDeportistaInputPort {

    RegistrarUsuarioResponse ejecutar(RegistrarDeportistaRequest request);

}