package com.utp.patrocinapp.application.dto.evidencia;

public record ArchivoEvidenciaCommand(String nombreOriginal, String tipoMime, long tamanioBytes,
                                      byte[] contenido, String comentario) {
    public ArchivoEvidenciaCommand {
        contenido = contenido == null ? null : contenido.clone();
    }

    @Override
    public byte[] contenido() {
        return contenido == null ? null : contenido.clone();
    }
}
