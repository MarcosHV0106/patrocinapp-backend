package com.utp.patrocinapp.infrastructure.persistence.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "perfil_deportista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilDeportistaEntity {

    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(nullable = false)
    private String disciplina;

}