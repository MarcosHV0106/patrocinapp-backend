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

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final RegistrarNegocioInputPort registrarNegocio;

    private final RegistrarDeportistaInputPort registrarDeportista;

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