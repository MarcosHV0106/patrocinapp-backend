package com.utp.patrocinapp.domain.ports.output;

import com.utp.patrocinapp.domain.model.AuditoriaAccion;

public interface AuditoriaRepositoryPort {
    AuditoriaAccion guardar(AuditoriaAccion auditoria);
}
