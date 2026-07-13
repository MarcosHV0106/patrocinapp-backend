package com.utp.patrocinapp.interfaces.rest;

import com.utp.patrocinapp.application.dto.notificacion.NotificacionResponse;
import com.utp.patrocinapp.domain.ports.input.ConsultarNotificacionesInputPort;
import com.utp.patrocinapp.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Notificaciones")
@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {
    private final ConsultarNotificacionesInputPort notificaciones;

    @Operation(summary = "Listar notificaciones del usuario autenticado")
    @GetMapping
    public ApiResponse<List<NotificacionResponse>> listar() {
        return ApiResponse.<List<NotificacionResponse>>builder().success(true)
                .message("Notificaciones obtenidas.").data(notificaciones.listar()).build();
    }

    @Operation(summary = "Marcar una notificación propia como leída")
    @PatchMapping("/{idNotificacion}/leida")
    public ApiResponse<NotificacionResponse> marcarLeida(@PathVariable Long idNotificacion) {
        return ApiResponse.<NotificacionResponse>builder().success(true)
                .message("Notificación marcada como leída.").data(notificaciones.marcarLeida(idNotificacion)).build();
    }
}
