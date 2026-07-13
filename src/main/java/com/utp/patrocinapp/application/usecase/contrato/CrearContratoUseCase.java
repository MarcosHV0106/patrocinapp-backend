package com.utp.patrocinapp.application.usecase.contrato;

import com.utp.patrocinapp.application.dto.contrato.CrearContratoRequest;
import com.utp.patrocinapp.application.dto.contrato.CrearContratoResponse;
import com.utp.patrocinapp.application.dto.contrato.MetaContratoCreadaResponse;
import com.utp.patrocinapp.application.dto.contrato.MetaSeleccionadaRequest;
import com.utp.patrocinapp.domain.model.Contrato;
import com.utp.patrocinapp.domain.model.FondoGarantia;
import com.utp.patrocinapp.domain.model.MetaContrato;
import com.utp.patrocinapp.domain.model.Notificacion;
import com.utp.patrocinapp.domain.model.AuditoriaAccion;
import com.utp.patrocinapp.domain.model.UsuarioAutenticado;
import com.utp.patrocinapp.domain.enums.Rol;
import com.utp.patrocinapp.domain.enums.TipoNotificacion;
import com.utp.patrocinapp.domain.ports.input.CrearContratoInputPort;
import com.utp.patrocinapp.domain.ports.output.ContratoRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.FondoGarantiaRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.MetaContratoRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.PerfilDeportistaRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.PerfilNegocioRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.UsuarioAutenticadoPort;
import com.utp.patrocinapp.domain.ports.output.NotificacionRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.AuditoriaRepositoryPort;
import com.utp.patrocinapp.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CrearContratoUseCase implements CrearContratoInputPort {

    private final ContratoRepositoryPort contratoRepository;
    private final FondoGarantiaRepositoryPort fondoGarantiaRepository;
    private final MetaContratoRepositoryPort metaContratoRepository;
    private final PerfilNegocioRepositoryPort perfilNegocioRepository;
    private final PerfilDeportistaRepositoryPort perfilDeportistaRepository;
    private final UsuarioAutenticadoPort usuarioAutenticadoPort;
    private final NotificacionRepositoryPort notificacionRepository;
    private final AuditoriaRepositoryPort auditoriaRepository;

    @Value("${patrocinapp.comision.porcentaje}")
    private BigDecimal porcentajeComision;

    @Override
    public CrearContratoResponse ejecutar(CrearContratoRequest request) {

        UsuarioAutenticado actor = usuarioAutenticadoPort.actual();
        if (!actor.tieneRol(Rol.NEGOCIO)) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "ROL_INCORRECTO",
                    "Solo un negocio puede crear contratos.");
        }
        if (request.getIdNegocio() != null && !actor.id().equals(request.getIdNegocio())) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "RECURSO_AJENO",
                    "No puedes crear un contrato en nombre de otro negocio.");
        }

        if (perfilNegocioRepository.buscarPorId(actor.id()).isEmpty()) {
            throw new BusinessException("El negocio indicado no existe.");
        }

        if (perfilDeportistaRepository.buscarPorId(request.getIdDeportista()).isEmpty()) {
            throw new BusinessException("El deportista indicado no existe.");
        }

        BigDecimal factorComision = BigDecimal.ONE.add(porcentajeComision.movePointLeft(2));
        BigDecimal montoTotalNegocio = request.getMetas()
                .stream()
                .map(MetaSeleccionadaRequest::getMontoDeportista)
                .map(monto -> monto.multiply(factorComision))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Contrato contrato = Contrato.crear(
                actor.id(),
                request.getIdDeportista(),
                montoTotalNegocio
        );

        contrato = contratoRepository.guardar(contrato);

        FondoGarantia fondo = FondoGarantia.crear(
                contrato.getIdContrato(),
                montoTotalNegocio
        );

        fondoGarantiaRepository.guardar(fondo);

        List<MetaContratoCreadaResponse> metasCreadas = new ArrayList<>();

        for (MetaSeleccionadaRequest item : request.getMetas()) {
            BigDecimal montoNegocio = item.getMontoDeportista()
                    .multiply(factorComision);

            MetaContrato meta = MetaContrato.crear(
                    item.getIdPlantilla(),
                    item.getDescripcionAcordada(),
                    item.getMontoDeportista(),
                    montoNegocio,
                    item.getComentarioDeportista()
            );

            meta.setIdContrato(contrato.getIdContrato());

            MetaContrato metaGuardada = metaContratoRepository.guardar(meta);

            metasCreadas.add(
                    MetaContratoCreadaResponse.builder()
                            .idMeta(metaGuardada.getIdMetaContrato())
                            .idPlantilla(metaGuardada.getIdPlantilla())
                            .descripcionAcordada(metaGuardada.getDescripcionAcordada())
                            .montoDeportista(metaGuardada.getMontoDeportista())
                            .estado(metaGuardada.getEstado())
                            .build()
            );
        }

        notificacionRepository.guardar(Notificacion.crear(request.getIdDeportista(),
                TipoNotificacion.CONTRATO_CREADO,
                "Un negocio creó un nuevo contrato de patrocinio contigo.",
                "CONTRATO", contrato.getIdContrato()));
        auditoriaRepository.guardar(AuditoriaAccion.registrar(actor.id(), "CONTRATO_CREADO", "CONTRATO",
                contrato.getIdContrato(), "EXITOSO", "Fondo de garantía interno creado."));

        return CrearContratoResponse.builder()
                .idContrato(contrato.getIdContrato())
                .montoTotal(montoTotalNegocio)
                .estado(contrato.getEstado())
                .metas(metasCreadas)
                .build();
    }
}
