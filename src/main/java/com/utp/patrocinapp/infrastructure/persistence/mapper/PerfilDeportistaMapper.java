package com.utp.patrocinapp.infrastructure.persistence.mapper;

import com.utp.patrocinapp.domain.model.PerfilDeportista;
import com.utp.patrocinapp.infrastructure.persistence.entity.PerfilDeportistaEntity;

public class PerfilDeportistaMapper {

    private PerfilDeportistaMapper() {
    }

    public static PerfilDeportista toDomain(PerfilDeportistaEntity entity) {

        if (entity == null) {
            return null;
        }

        return new PerfilDeportista(
                entity.getIdUsuario(),
                null, // El Usuario se recuperará cuando un caso de uso realmente lo necesite
                entity.getDni(),
                entity.getDisciplina()
        );

    }

    public static PerfilDeportistaEntity toEntity(PerfilDeportista domain) {

        if (domain == null) {
            return null;
        }

        return PerfilDeportistaEntity.builder()
                .idUsuario(domain.getIdUsuario())
                .dni(domain.getDni())
                .disciplina(domain.getDisciplina())
                .build();

    }

}