package com.utp.patrocinapp.interfaces.rest;

import com.utp.patrocinapp.application.dto.deportista.DeportistaCatalogoResponse;
import com.utp.patrocinapp.domain.ports.input.ListarDeportistasInputPort;
import com.utp.patrocinapp.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Deportistas",
        description = "Catálogo público de deportistas disponibles para patrocinio."
)
@RestController
@RequestMapping("/api/deportistas")
@RequiredArgsConstructor
public class DeportistaController {

    private final ListarDeportistasInputPort listarDeportistas;

    @Operation(summary = "Listar deportistas para el marketplace")
    @GetMapping
    public ResponseEntity<ApiResponse<List<DeportistaCatalogoResponse>>> listar(
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) String disciplina) {

        List<DeportistaCatalogoResponse> response = listarDeportistas.ejecutar(busqueda, disciplina);

        return ResponseEntity.ok(
                ApiResponse.<List<DeportistaCatalogoResponse>>builder()
                        .success(true)
                        .message("Deportistas obtenidos correctamente.")
                        .data(response)
                        .build()
        );
    }
}