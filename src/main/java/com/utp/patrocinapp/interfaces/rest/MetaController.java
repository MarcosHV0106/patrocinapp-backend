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

@RestController
@RequestMapping("/api/metas")
@RequiredArgsConstructor
public class MetaController {

    private final RegistrarEvidenciaInputPort registrarEvidencia;
    private final AprobarMetaInputPort aprobarMeta;

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