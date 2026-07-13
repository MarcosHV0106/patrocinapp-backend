package com.utp.patrocinapp.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones", uniqueConstraints = {
        @UniqueConstraint(name = "uq_transaccion_meta", columnNames = "id_meta_contrato")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Integer id;

    @Column(name = "id_contrato", nullable = false)
    private Integer idContrato;

    @Column(name = "id_meta_contrato", nullable = false)
    private Integer idMetaContrato;

    @Column(name = "monto_neto", nullable = false)
    private BigDecimal montoNeto;

    @Column(name = "comision_plataforma", nullable = false)
    private BigDecimal comisionPlataforma;

    @Column(name = "fecha_ejecucion")
    private LocalDateTime fechaEjecucion;

}
