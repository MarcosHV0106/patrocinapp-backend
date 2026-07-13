package com.utp.patrocinapp.application.dto.contrato;

import com.utp.patrocinapp.domain.model.Transaccion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransaccionResponse(Integer id, Integer idContrato, Integer idMetaContrato,
                                  BigDecimal montoNeto, BigDecimal comisionPlataforma,
                                  LocalDateTime fechaEjecucion) {
    public static TransaccionResponse from(Transaccion transaccion) {
        return new TransaccionResponse(transaccion.getId(), transaccion.getIdContrato(),
                transaccion.getIdMetaContrato(), transaccion.getMontoNeto(),
                transaccion.getComisionPlataforma(), transaccion.getFechaEjecucion());
    }
}
