package com.utp.patrocinapp.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_acciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditoriaAccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auditoria")
    private Long id;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(nullable = false, length = 100)
    private String accion;

    @Column(nullable = false, length = 80)
    private String entidad;

    @Column(name = "id_entidad", nullable = false, length = 80)
    private String idEntidad;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false, length = 40)
    private String resultado;

    @Column(name = "informacion_adicional", length = 1000)
    private String informacionAdicional;
}
