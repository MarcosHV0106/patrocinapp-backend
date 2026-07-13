package com.utp.patrocinapp.interfaces.rest;

import com.utp.patrocinapp.application.dto.dashboard.DashboardResumenResponse;
import com.utp.patrocinapp.domain.ports.input.ConsultarDashboardInputPort;
import com.utp.patrocinapp.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Dashboard")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final ConsultarDashboardInputPort dashboard;

    @Operation(summary = "Resumen financiero del deportista autenticado")
    @GetMapping("/deportista/resumen")
    public ApiResponse<DashboardResumenResponse> deportista() {
        return ApiResponse.<DashboardResumenResponse>builder().success(true)
                .message("Resumen del deportista obtenido.").data(dashboard.resumenDeportista()).build();
    }

    @Operation(summary = "Resumen financiero del negocio autenticado")
    @GetMapping("/negocio/resumen")
    public ApiResponse<DashboardResumenResponse> negocio() {
        return ApiResponse.<DashboardResumenResponse>builder().success(true)
                .message("Resumen del negocio obtenido.").data(dashboard.resumenNegocio()).build();
    }
}
