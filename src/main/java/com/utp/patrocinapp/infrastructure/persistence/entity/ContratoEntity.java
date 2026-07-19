package com.utp.patrocinapp.infrastructure.persistence.entity;

import com.utp.patrocinapp.domain.enums.EstadoContrato;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contratos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato")
    private Integer idContrato;

    @Column(name = "id_negocio", nullable = false)
    private Integer idNegocio;

    @Column(name = "id_deportista", nullable = false)
    private Integer idDeportista;

    @Column(name = "monto_total", nullable = false)
    private BigDecimal montoTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoContrato estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Version
    @Column(nullable = false)
    private Long version;

}
