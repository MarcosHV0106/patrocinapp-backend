package com.utp.patrocinapp.interfaces.rest;

import com.utp.patrocinapp.application.dto.contrato.CrearContratoRequest;
import com.utp.patrocinapp.application.dto.contrato.CrearContratoResponse;
import com.utp.patrocinapp.domain.ports.input.CrearContratoInputPort;
import com.utp.patrocinapp.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contratos")
@RequiredArgsConstructor
public class ContratoController {

    private final CrearContratoInputPort crearContrato;

    @PostMapping
    public ResponseEntity<ApiResponse<CrearContratoResponse>> crearContrato(
            @Valid @RequestBody CrearContratoRequest request) {

        CrearContratoResponse response = crearContrato.ejecutar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<CrearContratoResponse>builder()
                                .success(true)
                                .message("Contrato creado correctamente.")
                                .data(response)
                                .build()
                );
    }

}