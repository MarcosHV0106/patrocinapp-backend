package com.utp.patrocinapp.interfaces.rest;

import com.utp.patrocinapp.application.dto.evidencia.*;
import com.utp.patrocinapp.domain.ports.input.*;
import com.utp.patrocinapp.shared.exception.BusinessException;
import com.utp.patrocinapp.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name = "Evidencias", description = "Carga física, historial, revisión y descarga autorizada de evidencias.")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EvidenciaController {
    private final EnviarEvidenciaInputPort enviarEvidencia;
    private final ConsultarEvidenciasInputPort consultarEvidencias;
    private final DescargarEvidenciaInputPort descargarEvidencia;
    private final AprobarEvidenciaInputPort aprobarEvidencia;
    private final RechazarEvidenciaInputPort rechazarEvidencia;

    @Operation(summary = "Enviar o reenviar una evidencia",
            description = "Rol DEPORTISTA. Requiere ser propietario de la meta. Acepta JPEG, PNG, WebP, PDF y MP4 según configuración.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Evidencia enviada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Archivo inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Sesión inválida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Recurso ajeno"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Estado o archivo duplicado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "413", description = "Archivo demasiado grande")
    })
    @PostMapping(value = "/metas/{idMeta}/evidencias", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<EvidenciaResponse>> enviar(
            @PathVariable Integer idMeta,
            @RequestPart("archivo") @Schema(type = "string", format = "binary") MultipartFile archivo,
            @RequestParam(required = false) String comentario) {
        try {
            ArchivoEvidenciaCommand command = new ArchivoEvidenciaCommand(archivo.getOriginalFilename(),
                    archivo.getContentType(), archivo.getSize(), archivo.getBytes(), comentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<EvidenciaResponse>builder()
                    .success(true).message("Evidencia enviada para revisión.")
                    .data(enviarEvidencia.ejecutar(idMeta, command)).build());
        } catch (IOException ex) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "ARCHIVO_ILEGIBLE",
                    "No se pudo leer el archivo seleccionado.");
        }
    }

    @Operation(summary = "Consultar historial de evidencias de una meta", description = "Disponible para las dos partes del contrato.")
    @GetMapping("/metas/{idMeta}/evidencias")
    public ApiResponse<List<EvidenciaResponse>> listar(@PathVariable Integer idMeta) {
        return ApiResponse.<List<EvidenciaResponse>>builder().success(true)
                .message("Historial de evidencias obtenido.").data(consultarEvidencias.listarPorMeta(idMeta)).build();
    }

    @Operation(summary = "Consultar evidencia actualmente en revisión")
    @GetMapping("/metas/{idMeta}/evidencias/actual")
    public ApiResponse<EvidenciaResponse> actual(@PathVariable Integer idMeta) {
        return ApiResponse.<EvidenciaResponse>builder().success(true)
                .message("Evidencia actual obtenida.").data(consultarEvidencias.consultarActual(idMeta)).build();
    }

    @Operation(summary = "Consultar metadatos de una evidencia")
    @GetMapping("/evidencias/{idEvidencia}")
    public ApiResponse<EvidenciaResponse> detalle(@PathVariable Long idEvidencia) {
        return ApiResponse.<EvidenciaResponse>builder().success(true)
                .message("Evidencia obtenida.").data(consultarEvidencias.consultarPorId(idEvidencia)).build();
    }

    @Operation(summary = "Descargar archivo de evidencia", description = "La respuesta es binaria y exige pertenecer al contrato.")
    @GetMapping("/evidencias/{idEvidencia}/archivo")
    public ResponseEntity<byte[]> archivo(@PathVariable Long idEvidencia) {
        ArchivoEvidenciaResponse archivo = descargarEvidencia.ejecutar(idEvidencia);
        ContentDisposition disposition = ContentDisposition.attachment()
                .filename(archivo.nombreOriginal(), StandardCharsets.UTF_8).build();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(archivo.tipoMime()))
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
                .header(HttpHeaders.CACHE_CONTROL, "private, no-store")
                .body(archivo.contenido());
    }

    @Operation(summary = "Aprobar evidencia y liberar pago", description = "Rol NEGOCIO propietario. Operación transaccional e idempotente.")
    @PostMapping("/evidencias/{idEvidencia}/aprobar")
    public ApiResponse<AprobarEvidenciaResponse> aprobar(@PathVariable Long idEvidencia) {
        return ApiResponse.<AprobarEvidenciaResponse>builder().success(true)
                .message("Evidencia aprobada y pago liberado.").data(aprobarEvidencia.ejecutar(idEvidencia)).build();
    }

    @Operation(summary = "Rechazar evidencia con motivo", description = "Rol NEGOCIO propietario. No libera fondos.")
    @PostMapping("/evidencias/{idEvidencia}/rechazar")
    public ApiResponse<EvidenciaResponse> rechazar(@PathVariable Long idEvidencia,
                                                    @Valid @RequestBody RechazarEvidenciaRequest request) {
        return ApiResponse.<EvidenciaResponse>builder().success(true)
                .message("Evidencia rechazada; el deportista puede reenviar.")
                .data(rechazarEvidencia.ejecutar(idEvidencia, request.getMotivo())).build();
    }
}
