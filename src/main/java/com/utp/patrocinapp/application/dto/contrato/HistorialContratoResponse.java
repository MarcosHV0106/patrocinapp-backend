package com.utp.patrocinapp.application.dto.contrato;

import com.utp.patrocinapp.application.dto.evidencia.EvidenciaResponse;

import java.util.List;

public record HistorialContratoResponse(Integer idContrato, List<EvidenciaResponse> evidencias,
                                        List<TransaccionResponse> transacciones) {
}
