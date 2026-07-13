package com.utp.patrocinapp.application.dto.dashboard;

import java.math.BigDecimal;

public record DashboardResumenResponse(long contratos, long metas, long metasPendientes,
                                       long evidenciasEnRevision, long metasPagadas,
                                       BigDecimal montoComprometido, BigDecimal montoRetenido,
                                       BigDecimal montoLiberado, BigDecimal comisionPlataforma) {
}
