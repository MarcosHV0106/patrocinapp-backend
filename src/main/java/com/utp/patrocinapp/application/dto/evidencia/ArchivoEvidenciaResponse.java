package com.utp.patrocinapp.application.dto.evidencia;

public record ArchivoEvidenciaResponse(String nombreOriginal, String tipoMime, byte[] contenido) {
    public ArchivoEvidenciaResponse {
        contenido = contenido.clone();
    }

    @Override
    public byte[] contenido() {
        return contenido.clone();
    }
}
