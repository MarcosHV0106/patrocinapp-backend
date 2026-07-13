package com.utp.patrocinapp.application.usecase.contrato;

import com.utp.patrocinapp.application.dto.contrato.ContratoDetalleResponse;
import com.utp.patrocinapp.application.dto.contrato.MetaContratoDetalleResponse;
import com.utp.patrocinapp.domain.model.Contrato;
import com.utp.patrocinapp.domain.model.MetaContrato;
import com.utp.patrocinapp.domain.model.PerfilDeportista;
import com.utp.patrocinapp.domain.model.PerfilNegocio;
import com.utp.patrocinapp.domain.ports.input.ListarContratosInputPort;
import com.utp.patrocinapp.domain.ports.output.ContratoRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.MetaContratoRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.PerfilDeportistaRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.PerfilNegocioRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.UsuarioAutenticadoPort;
import com.utp.patrocinapp.domain.ports.output.EvidenciaRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.FondoGarantiaRepositoryPort;
import com.utp.patrocinapp.domain.enums.Rol;
import com.utp.patrocinapp.domain.model.UsuarioAutenticado;
import com.utp.patrocinapp.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;
import com.utp.patrocinapp.application.dto.evidencia.EvidenciaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarContratosUseCase implements ListarContratosInputPort {

    private final ContratoRepositoryPort contratoRepository;
    private final MetaContratoRepositoryPort metaRepository;
    private final PerfilNegocioRepositoryPort perfilNegocioRepository;
    private final PerfilDeportistaRepositoryPort perfilDeportistaRepository;
    private final UsuarioAutenticadoPort usuarioAutenticadoPort;
    private final EvidenciaRepositoryPort evidenciaRepository;
    private final FondoGarantiaRepositoryPort fondoRepository;

    @Override
    public List<ContratoDetalleResponse> listarPorNegocio(Integer idNegocio) {
        validarPropietario(idNegocio, Rol.NEGOCIO);
        return contratoRepository.buscarPorNegocio(idNegocio)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<ContratoDetalleResponse> listarPorDeportista(Integer idDeportista) {
        validarPropietario(idDeportista, Rol.DEPORTISTA);
        return contratoRepository.buscarPorDeportista(idDeportista)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ContratoDetalleResponse toResponse(Contrato contrato) {
        PerfilNegocio negocio = perfilNegocioRepository.buscarPorId(contrato.getIdNegocio())
                .orElse(null);

        PerfilDeportista deportista = perfilDeportistaRepository.buscarPorId(contrato.getIdDeportista())
                .orElse(null);

        var fondo = fondoRepository.buscarPorContrato(contrato.getIdContrato()).orElse(null);

        return ContratoDetalleResponse.builder()
                .idContrato(contrato.getIdContrato())
                .idNegocio(contrato.getIdNegocio())
                .razonSocialNegocio(negocio != null ? negocio.getRazonSocial() : "Negocio #" + contrato.getIdNegocio())
                .idDeportista(contrato.getIdDeportista())
                .nombreDeportista(deportista != null ? deportista.getNombreCompleto() : "Deportista #" + contrato.getIdDeportista())
                .disciplinaDeportista(deportista != null ? deportista.getDisciplina() : null)
                .montoTotal(contrato.getMontoTotal())
                .montoRetenido(fondo != null ? fondo.getMontoCongelado() : null)
                .montoLiberado(fondo != null ? fondo.getMontoLiberado() : null)
                .estado(contrato.getEstado())
                .fechaCreacion(contrato.getFechaCreacion())
                .metas(metaRepository.listarPorContrato(contrato.getIdContrato())
                        .stream()
                        .map(this::toMetaResponse)
                        .toList())
                .build();
    }

    private MetaContratoDetalleResponse toMetaResponse(MetaContrato meta) {
        var evidencias = evidenciaRepository.listarPorMeta(meta.getIdMetaContrato()).stream()
                .map(EvidenciaResponse::from).toList();
        return MetaContratoDetalleResponse.builder()
                .idMeta(meta.getIdMetaContrato())
                .idPlantilla(meta.getIdPlantilla())
                .descripcionAcordada(meta.getDescripcionAcordada())
                .montoDeportista(meta.getMontoDeportista())
                .montoNegocio(meta.getMontoNegocio())
                .comentarioDeportista(meta.getComentarioDeportista())
                .urlEvidencia(meta.getUrlEvidencia())
                .estado(meta.getEstado())
                .evidenciaActual(evidencias.stream()
                        .filter(item -> item.estado() == com.utp.patrocinapp.domain.enums.EstadoEvidencia.EN_REVISION)
                        .findFirst().orElse(evidencias.isEmpty() ? null : evidencias.get(0)))
                .evidencias(evidencias)
                .build();
    }

    private void validarPropietario(Integer idSolicitado, Rol rol) {
        UsuarioAutenticado actor = usuarioAutenticadoPort.actual();
        if (!actor.tieneRol(rol) || !actor.id().equals(idSolicitado)) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "RECURSO_AJENO",
                    "No puedes consultar contratos de otro usuario.");
        }
    }
}
