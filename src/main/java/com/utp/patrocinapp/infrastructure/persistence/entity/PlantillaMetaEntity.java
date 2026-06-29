package com.utp.patrocinapp.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "plantilla_metas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantillaMetaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plantilla")
    private Integer id;

    @Column(name = "nombre_meta")
    private String nombreMeta;

    @Column(name = "descripcion_sugerida")
    private String descripcionSugerida;

    @Column(name = "tipo_entregable")
    private String tipoEntregable;

    @Column(name = "precio_sugerido")
    private BigDecimal precioSugerido;

}