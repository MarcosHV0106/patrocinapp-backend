package com.utp.patrocinapp.interfaces.rest;

import com.utp.patrocinapp.application.dto.meta.AprobarMetaResponse;
import com.utp.patrocinapp.application.dto.meta.RegistrarEvidenciaRequest;
import com.utp.patrocinapp.domain.ports.input.AprobarMetaInputPort;
import com.utp.patrocinapp.domain.ports.input.RegistrarEvidenciaInputPort;
import com.utp.patrocinapp.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Metas",
        description = "Gestión de evidencias y aprobación de metas."
)
@RestController
@RequestMapping("/api/metas")
@RequiredArgsConstructor
public class MetaController {

    private final RegistrarEvidenciaInputPort registrarEvidencia;
    private final AprobarMetaInputPort aprobarMeta;

    @Deprecated
    @Operation(summary = "Registrar evidencia por URL (legado)", deprecated = true)
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Evidencia registrada correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Meta no encontrada")
    })
    @PutMapping("/{id}/evidencia")
    public ResponseEntity<ApiResponse<Void>> registrarEvidencia(
            @PathVariable Integer id,
            @Valid @RequestBody RegistrarEvidenciaRequest request) {

        registrarEvidencia.ejecutar(id, request);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Evidencia registrada correctamente.")
                        .build()
        );
    }

    @Deprecated
    @Operation(summary = "Aprobar una meta por su evidencia actual (legado)", deprecated = true)
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Meta aprobada correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Meta no encontrada")
    })
    @PostMapping("/{id}/aprobar")
    public ResponseEntity<ApiResponse<AprobarMetaResponse>> aprobarMeta(
            @PathVariable Integer id) {

        AprobarMetaResponse response = aprobarMeta.ejecutar(id);

        return ResponseEntity.ok(
                ApiResponse.<AprobarMetaResponse>builder()
                        .success(true)
                        .message("Meta aprobada correctamente.")
                        .data(response)
                        .build()
        );
    }

}
