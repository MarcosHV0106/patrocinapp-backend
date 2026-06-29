package com.utp.patrocinapp.infrastructure.persistence.mapper;
import com.utp.patrocinapp.domain.model.Usuario;
import com.utp.patrocinapp.infrastructure.persistence.entity.UsuarioEntity;

public class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static Usuario toDomain(UsuarioEntity entity) {

        if (entity == null) {
            return null;
        }

        return new Usuario(
                entity.getId(),
                entity.getCorreo(),
                entity.getPasswordHash(),
                entity.getRol(),
                entity.getFechaRegistro()
        );
    }

    public static UsuarioEntity toEntity(Usuario domain) {

        if (domain == null) {
            return null;
        }

        return UsuarioEntity.builder()
                .id(domain.getId())
                .correo(domain.getCorreo())
                .passwordHash(domain.getPasswordHash())
                .rol(domain.getRol())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }
}