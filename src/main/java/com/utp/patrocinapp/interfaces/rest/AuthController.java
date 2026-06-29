package com.utp.patrocinapp.interfaces.rest;

import com.utp.patrocinapp.application.dto.auth.LoginRequest;
import com.utp.patrocinapp.application.dto.auth.LoginResponse;
import com.utp.patrocinapp.domain.ports.input.LoginInputPort;
import com.utp.patrocinapp.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginInputPort loginInputPort;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = loginInputPort.ejecutar(request);

        return ResponseEntity.ok(
                ApiResponse.<LoginResponse>builder()
                        .success(true)
                        .message("Inicio de sesión exitoso.")
                        .data(response)
                        .build()
        );
    }

}