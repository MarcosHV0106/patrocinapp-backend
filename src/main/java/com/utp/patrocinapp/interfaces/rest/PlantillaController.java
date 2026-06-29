package com.utp.patrocinapp.interfaces.rest;

import com.utp.patrocinapp.application.dto.plantilla.PlantillaMetaResponse;
import com.utp.patrocinapp.domain.ports.input.ListarPlantillasInputPort;
import com.utp.patrocinapp.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantillas")
@RequiredArgsConstructor
public class PlantillaController {

    private final ListarPlantillasInputPort listarPlantillas;

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