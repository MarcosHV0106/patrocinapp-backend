package com.utp.patrocinapp.interfaces.rest;

import com.utp.patrocinapp.application.dto.contrato.ContratoDetalleResponse;
import com.utp.patrocinapp.application.dto.contrato.CrearContratoRequest;
import com.utp.patrocinapp.application.dto.contrato.CrearContratoResponse;
import com.utp.patrocinapp.domain.ports.input.CrearContratoInputPort;
import com.utp.patrocinapp.domain.ports.input.ListarContratosInputPort;
import com.utp.patrocinapp.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(
        name = "Contratos",
        description = "Gestión de contratos de patrocinio."
)
@RestController
@RequestMapping("/api/contratos")
@RequiredArgsConstructor
public class ContratoController {

    private final CrearContratoInputPort crearContrato;
    private final ListarContratosInputPort listarContratos;

    @Operation(summary = "Crear un contrato")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Contrato creado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida")
    })
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

    @Operation(summary = "Listar contratos creados por un negocio")
    @GetMapping("/negocio/{idNegocio}")
    public ResponseEntity<ApiResponse<List<ContratoDetalleResponse>>> listarPorNegocio(
            @PathVariable Integer idNegocio) {

        return ResponseEntity.ok(
                ApiResponse.<List<ContratoDetalleResponse>>builder()
                        .success(true)
                        .message("Contratos del negocio obtenidos correctamente.")
                        .data(listarContratos.listarPorNegocio(idNegocio))
                        .build()
        );
    }

    @Operation(summary = "Listar contratos asociados a un deportista")
    @GetMapping("/deportista/{idDeportista}")
    public ResponseEntity<ApiResponse<List<ContratoDetalleResponse>>> listarPorDeportista(
            @PathVariable Integer idDeportista) {

        return ResponseEntity.ok(
                ApiResponse.<List<ContratoDetalleResponse>>builder()
                        .success(true)
                        .message("Contratos del deportista obtenidos correctamente.")
                        .data(listarContratos.listarPorDeportista(idDeportista))
                        .build()
        );
    }
}