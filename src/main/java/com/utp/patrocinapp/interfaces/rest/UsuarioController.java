package com.utp.patrocinapp.interfaces.rest;

import com.utp.patrocinapp.application.dto.usuario.RegistrarDeportistaRequest;
import com.utp.patrocinapp.application.dto.usuario.RegistrarNegocioRequest;
import com.utp.patrocinapp.application.dto.usuario.RegistrarUsuarioResponse;
import com.utp.patrocinapp.domain.ports.input.RegistrarDeportistaInputPort;
import com.utp.patrocinapp.domain.ports.input.RegistrarNegocioInputPort;
import com.utp.patrocinapp.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(
        name = "Usuarios",
        description = "Registro de deportistas y negocios"
)
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final RegistrarNegocioInputPort registrarNegocio;

    private final RegistrarDeportistaInputPort registrarDeportista;

    @Operation(summary = "Registrar un nuevo negocio")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Negocio registrado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos")
    })
    @PostMapping("/negocios")
    public ResponseEntity<ApiResponse<RegistrarUsuarioResponse>> registrarNegocio(
            @Valid @RequestBody RegistrarNegocioRequest request) {

        RegistrarUsuarioResponse response = registrarNegocio.ejecutar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<RegistrarUsuarioResponse>builder()
                                .success(true)
                                .message("Negocio registrado correctamente.")
                                .data(response)
                                .build()
                );

    }

    @Operation(summary = "Registrar un nuevo deportista")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Deportista registrado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos")
    })
    @PostMapping("/deportistas")
    public ResponseEntity<ApiResponse<RegistrarUsuarioResponse>> registrarDeportista(
            @Valid @RequestBody RegistrarDeportistaRequest request) {

        RegistrarUsuarioResponse response = registrarDeportista.ejecutar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<RegistrarUsuarioResponse>builder()
                                .success(true)
                                .message("Deportista registrado correctamente.")
                                .data(response)
                                .build()
                );
    }

}