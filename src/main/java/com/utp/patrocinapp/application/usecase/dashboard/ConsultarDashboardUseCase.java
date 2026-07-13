package com.utp.patrocinapp.application.usecase.dashboard;

import com.utp.patrocinapp.application.dto.dashboard.DashboardResumenResponse;
import com.utp.patrocinapp.domain.enums.EstadoMeta;
import com.utp.patrocinapp.domain.enums.Rol;
import com.utp.patrocinapp.domain.model.*;
import com.utp.patrocinapp.domain.ports.input.ConsultarDashboardInputPort;
import com.utp.patrocinapp.domain.ports.output.*;
import com.utp.patrocinapp.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultarDashboardUseCase implements ConsultarDashboardInputPort {
    private final UsuarioAutenticadoPort usuarioAutenticadoPort;
    private final ContratoRepositoryPort contratoRepository;
    private final MetaContratoRepositoryPort metaRepository;
    private final FondoGarantiaRepositoryPort fondoRepository;
    private final TransaccionRepositoryPort transaccionRepository;

    @Override
    public DashboardResumenResponse resumenDeportista() {
        UsuarioAutenticado actor = requerirRol(Rol.DEPORTISTA);
        return calcular(contratoRepository.buscarPorDeportista(actor.id()));
    }

    @Override
    public DashboardResumenResponse resumenNegocio() {
        UsuarioAutenticado actor = requerirRol(Rol.NEGOCIO);
        return calcular(contratoRepository.buscarPorNegocio(actor.id()));
    }

    private DashboardResumenResponse calcular(List<Contrato> contratos) {
        List<MetaContrato> metas = contratos.stream()
                .flatMap(c -> metaRepository.listarPorContrato(c.getIdContrato()).stream()).toList();
        List<FondoGarantia> fondos = contratos.stream()
                .map(c -> fondoRepository.buscarPorContrato(c.getIdContrato()).orElse(null))
                .filter(java.util.Objects::nonNull).toList();
        List<Transaccion> transacciones = contratos.stream()
                .flatMap(c -> transaccionRepository.listarPorContrato(c.getIdContrato()).stream()).toList();

        return new DashboardResumenResponse(contratos.size(), metas.size(),
                metas.stream().filter(m -> m.getEstado() == EstadoMeta.PENDIENTE
                        || m.getEstado() == EstadoMeta.RECHAZADA).count(),
                metas.stream().filter(m -> m.getEstado() == EstadoMeta.EN_REVISION).count(),
                metas.stream().filter(m -> m.getEstado() == EstadoMeta.PAGADA).count(),
                contratos.stream().map(Contrato::getMontoTotal).reduce(BigDecimal.ZERO, BigDecimal::add),
                fondos.stream().map(FondoGarantia::getMontoCongelado).reduce(BigDecimal.ZERO, BigDecimal::add),
                transacciones.stream().map(Transaccion::getMontoNeto).reduce(BigDecimal.ZERO, BigDecimal::add),
                transacciones.stream().map(Transaccion::getComisionPlataforma).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private UsuarioAutenticado requerirRol(Rol rol) {
        UsuarioAutenticado actor = usuarioAutenticadoPort.actual();
        if (!actor.tieneRol(rol)) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "ROL_INCORRECTO",
                    "El resumen solicitado no corresponde al rol de tu cuenta.");
        }
        return actor;
    }
}
