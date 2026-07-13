package com.utp.patrocinapp.infrastructure.persistence.entity;

import com.utp.patrocinapp.domain.enums.EstadoEvidencia;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "evidencias", uniqueConstraints = {
        @UniqueConstraint(name = "uq_evidencia_meta_intento", columnNames = {"id_meta_contrato", "numero_intento"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvidenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evidencia")
    private Long idEvidencia;

    @Column(name = "id_meta_contrato", nullable = false)
    private Integer idMetaContrato;

    @Column(name = "numero_intento", nullable = false)
    private int numeroIntento;

    @Column(name = "nombre_original", nullable = false, length = 255)
    private String nombreOriginal;

    @Column(name = "tipo_mime", nullable = false, length = 100)
    private String tipoMime;

    @Column(name = "tamanio_bytes", nullable = false)
    private long tamanioBytes;

    @Column(name = "hash_sha256", nullable = false, length = 64)
    private String hashSha256;

    @Column(name = "comentario_deportista", length = 1000)
    private String comentarioDeportista;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoEvidencia estado;

    @Column(name = "motivo_rechazo", length = 1000)
    private String motivoRechazo;

    @Column(name = "fecha_carga", nullable = false)
    private LocalDateTime fechaCarga;

    @Column(name = "fecha_revision")
    private LocalDateTime fechaRevision;

    @Column(name = "id_usuario_revisor")
    private Integer idUsuarioRevisor;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @Version
    @Column(nullable = false)
    private Long version;
}
