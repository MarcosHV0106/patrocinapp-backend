package com.utp.patrocinapp.infrastructure.persistence.entity;

import com.utp.patrocinapp.domain.enums.EstadoMeta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "metas_contrato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaContratoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_meta_contrato")
    private Integer idMetaContrato;

    @Column(name = "id_contrato", nullable = false)
    private Integer idContrato;

    @Column(name = "id_plantilla", nullable = false)
    private Integer idPlantilla;

    @Column(name = "descripcion_acordada", nullable = false)
    private String descripcionAcordada;

    @Column(name = "monto_deportista", nullable = false)
    private BigDecimal montoDeportista;

    @Column(name = "monto_negocio", nullable = false)
    private BigDecimal montoNegocio;

    @Column(name = "comentario_deportista")
    private String comentarioDeportista;

    @Column(name = "url_evidencia")
    private String urlEvidencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMeta estado;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Version
    @Column(nullable = false)
    private Long version;

}
