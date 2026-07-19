package com.utp.patrocinapp.application.service;

import com.utp.patrocinapp.application.dto.evidencia.ArchivoEvidenciaCommand;
import com.utp.patrocinapp.shared.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidadorArchivoEvidencia {
    private final long maxBytes;
    private final Set<String> tiposPermitidos;

    public ValidadorArchivoEvidencia(@Value("${patrocinapp.evidencias.max-bytes}") long maxBytes,
                                     @Value("${patrocinapp.evidencias.tipos-permitidos}") String tipos) {
        this.maxBytes = maxBytes;
        this.tiposPermitidos = Arrays.stream(tipos.split(","))
                .map(String::trim).filter(value -> !value.isBlank()).collect(Collectors.toUnmodifiableSet());
    }

    public ArchivoValidado validar(ArchivoEvidenciaCommand command) {
        if (command == null || command.contenido() == null || command.contenido().length == 0) {
            throw error(HttpStatus.BAD_REQUEST, "ARCHIVO_VACIO", "Selecciona un archivo con contenido.");
        }
        byte[] contenido = command.contenido();
        if (contenido.length > maxBytes || command.tamanioBytes() > maxBytes) {
            throw error(HttpStatus.PAYLOAD_TOO_LARGE, "ARCHIVO_DEMASIADO_GRANDE",
                    "El archivo supera el tamaño máximo permitido.");
        }
        if (command.tamanioBytes() != contenido.length) {
            throw error(HttpStatus.BAD_REQUEST, "TAMANIO_ARCHIVO_INVALIDO",
                    "El tamaño declarado del archivo no coincide con su contenido.");
        }

        String nombre = limpiarNombre(command.nombreOriginal());
        String mimeDeclarado = command.tipoMime() == null ? "" : command.tipoMime().trim().toLowerCase();
        if (!tiposPermitidos.contains(mimeDeclarado)) {
            throw error(HttpStatus.BAD_REQUEST, "ARCHIVO_NO_PERMITIDO",
                    "El archivo seleccionado no tiene un formato permitido.");
        }

        String mimeDetectado = detectarMime(contenido);
        if (mimeDetectado == null || !mimeDeclarado.equals(mimeDetectado)) {
            throw error(HttpStatus.BAD_REQUEST, "FIRMA_ARCHIVO_INVALIDA",
                    "El contenido del archivo no coincide con el formato declarado.");
        }

        return new ArchivoValidado(nombre, mimeDetectado, contenido.length, sha256(contenido), contenido);
    }

    private String limpiarNombre(String original) {
        if (original == null || original.isBlank()) {
            throw error(HttpStatus.BAD_REQUEST, "NOMBRE_ARCHIVO_INVALIDO", "El archivo debe tener un nombre válido.");
        }
        String normalizado = original.replace('\\', '/');
        String nombre = normalizado.substring(normalizado.lastIndexOf('/') + 1).replaceAll("[\\r\\n]", "").trim();
        if (nombre.isBlank() || nombre.length() > 255) {
            throw error(HttpStatus.BAD_REQUEST, "NOMBRE_ARCHIVO_INVALIDO",
                    "El nombre del archivo está vacío o es demasiado largo.");
        }
        return nombre;
    }

    private String detectarMime(byte[] bytes) {
        if (bytes.length >= 3 && (bytes[0] & 0xff) == 0xff && (bytes[1] & 0xff) == 0xd8
                && (bytes[2] & 0xff) == 0xff) return "image/jpeg";
        if (bytes.length >= 8 && Arrays.equals(Arrays.copyOf(bytes, 8),
                new byte[]{(byte) 0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a})) return "image/png";
        if (bytes.length >= 12 && ascii(bytes, 0, 4).equals("RIFF") && ascii(bytes, 8, 4).equals("WEBP"))
            return "image/webp";
        if (bytes.length >= 5 && ascii(bytes, 0, 5).equals("%PDF-")) return "application/pdf";
        if (bytes.length >= 12 && ascii(bytes, 4, 4).equals("ftyp")) return "video/mp4";
        return null;
    }

    private String ascii(byte[] bytes, int offset, int length) {
        return new String(bytes, offset, length, StandardCharsets.US_ASCII);
    }

    private String sha256(byte[] contenido) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(contenido));
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 no está disponible en el runtime.", ex);
        }
    }

    private BusinessException error(HttpStatus status, String code, String message) {
        return new BusinessException(status, code, message);
    }
}
