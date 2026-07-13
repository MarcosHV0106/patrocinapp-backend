package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.dashboard.DashboardResumenResponse;

public interface ConsultarDashboardInputPort {
    DashboardResumenResponse resumenDeportista();
    DashboardResumenResponse resumenNegocio();
}
