package com.utp.patrocinapp.infrastructure.persistence.adapter;

import com.utp.patrocinapp.domain.ports.output.ArchivoEvidenciaStoragePort;
import com.utp.patrocinapp.infrastructure.persistence.entity.ArchivoEvidenciaEntity;
import com.utp.patrocinapp.infrastructure.persistence.repository.ArchivoEvidenciaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArchivoEvidenciaStorageAdapter implements ArchivoEvidenciaStoragePort {
    private final ArchivoEvidenciaJpaRepository repository;

    @Override
    public void guardar(Long idEvidencia, byte[] contenido) {
        repository.save(new ArchivoEvidenciaEntity(idEvidencia, contenido.clone()));
    }

    @Override
    public Optional<byte[]> obtener(Long idEvidencia) {
        return repository.findById(idEvidencia).map(ArchivoEvidenciaEntity::getContenido).map(byte[]::clone);
    }
}
