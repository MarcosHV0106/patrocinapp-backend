package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.auth.LoginRequest;
import com.utp.patrocinapp.application.dto.auth.LoginResponse;

public interface LoginInputPort {

    LoginResponse ejecutar(LoginRequest request);

}