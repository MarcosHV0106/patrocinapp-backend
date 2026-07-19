package com.utp.patrocinapp.application.dto.evidencia;

import com.utp.patrocinapp.domain.enums.EstadoContrato;

import java.math.BigDecimal;

public record AprobarEvidenciaResponse(Long idEvidencia, Integer idMeta, String estadoMeta,
                                       BigDecimal montoNeto, BigDecimal comisionPlataforma,
                                       BigDecimal saldoRetenido, EstadoContrato estadoContrato) {
}
