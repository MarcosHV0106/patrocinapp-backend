package com.utp.patrocinapp.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fondo_garantia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FondoGarantiaEntity {

    @Id
    @Column(name = "id_contrato")
    private Integer idContrato;

    @Column(name = "monto_congelado", nullable = false)
    private BigDecimal montoCongelado;

    @Column(name = "monto_inicial", nullable = false)
    private BigDecimal montoInicial;

    @Column(name = "monto_liberado", nullable = false)
    private BigDecimal montoLiberado;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    @Version
    @Column(nullable = false)
    private Long version;

}
