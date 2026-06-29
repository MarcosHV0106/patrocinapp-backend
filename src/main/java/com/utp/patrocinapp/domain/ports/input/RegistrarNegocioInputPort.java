package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.usuario.RegistrarNegocioRequest;
import com.utp.patrocinapp.application.dto.usuario.RegistrarUsuarioResponse;

public interface RegistrarNegocioInputPort {

    RegistrarUsuarioResponse ejecutar(RegistrarNegocioRequest request);

}