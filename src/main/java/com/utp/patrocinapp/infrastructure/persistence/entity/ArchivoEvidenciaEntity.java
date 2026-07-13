package com.utp.patrocinapp.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "archivos_evidencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArchivoEvidenciaEntity {
    @Id
    @Column(name = "id_evidencia")
    private Long idEvidencia;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "contenido", nullable = false, columnDefinition = "BYTEA")
    private byte[] contenido;
}
