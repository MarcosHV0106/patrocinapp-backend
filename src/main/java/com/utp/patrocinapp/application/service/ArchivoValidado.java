package com.utp.patrocinapp.application.service;

public record ArchivoValidado(String nombreOriginal, String tipoMime, long tamanioBytes,
                              String hashSha256, byte[] contenido) {
    public ArchivoValidado {
        contenido = contenido.clone();
    }

    @Override
    public byte[] contenido() {
        return contenido.clone();
    }
}
