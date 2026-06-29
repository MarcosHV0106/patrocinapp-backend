package com.utp.patrocinapp.interfaces.rest;

import com.utp.patrocinapp.application.dto.plantilla.PlantillaMetaResponse;
import com.utp.patrocinapp.domain.ports.input.ListarPlantillasInputPort;
import com.utp.patrocinapp.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(
        name = "Plantillas",
        description = "Catálogo de metas disponibles para patrocinio."
)
@RestController
@RequestMapping("/api/plantillas")
@RequiredArgsConstructor
public class PlantillaController {

    private final ListarPlantillasInputPort listarPlantillas;

    @Operation(summary = "Obtener catálogo de plantillas")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Plantillas obtenidas correctamente")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<PlantillaMetaResponse>>> listar() {

        List<PlantillaMetaResponse> response =
                listarPlantillas.ejecutar();

        return ResponseEntity.ok(
                ApiResponse.<List<PlantillaMetaResponse>>builder()
                        .success(true)
                        .message("Plantillas obtenidas correctamente.")
                        .data(response)
                        .build()
        );

    }

}