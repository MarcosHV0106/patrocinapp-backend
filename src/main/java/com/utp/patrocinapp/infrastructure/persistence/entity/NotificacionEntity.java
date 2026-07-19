package com.utp.patrocinapp.infrastructure.persistence.entity;

import com.utp.patrocinapp.domain.enums.TipoNotificacion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Long id;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoNotificacion tipo;

    @Column(nullable = false, length = 500)
    private String mensaje;

    @Column(name = "entidad_relacionada", nullable = false, length = 80)
    private String entidadRelacionada;

    @Column(name = "id_entidad", nullable = false, length = 80)
    private String idEntidad;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private boolean leida;

    @Column(name = "fecha_lectura")
    private LocalDateTime fechaLectura;
}
