package com.utp.patrocinapp.infrastructure.persistence.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "perfil_negocio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilNegocioEntity {

    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(nullable = false, unique = true, length = 11)
    private String ruc;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

}