package com.utp.patrocinapp.interfaces.rest;

import com.utp.patrocinapp.application.dto.auth.LoginRequest;
import com.utp.patrocinapp.application.dto.auth.LoginResponse;
import com.utp.patrocinapp.domain.ports.input.LoginInputPort;
import com.utp.patrocinapp.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Autenticación",
        description = "Operaciones relacionadas con el inicio de sesión."
)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginInputPort loginInputPort;

    @Operation(summary = "Iniciar sesión")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Inicio de sesión exitoso"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Credenciales inválidas")
    })
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