package com.utp.patrocinapp.domain.ports.output;

import java.util.Optional;

public interface ArchivoEvidenciaStoragePort {
    void guardar(Long idEvidencia, byte[] contenido);
    Optional<byte[]> obtener(Long idEvidencia);
}
