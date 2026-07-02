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

    @Override
    public List<ContratoDetalleResponse> listarPorNegocio(Integer idNegocio) {
        return contratoRepository.buscarPorNegocio(idNegocio)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<ContratoDetalleResponse> listarPorDeportista(Integer idDeportista) {
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

        return ContratoDetalleResponse.builder()
                .idContrato(contrato.getIdContrato())
                .idNegocio(contrato.getIdNegocio())
                .razonSocialNegocio(negocio != null ? negocio.getRazonSocial() : "Negocio #" + contrato.getIdNegocio())
                .idDeportista(contrato.getIdDeportista())
                .nombreDeportista(deportista != null ? deportista.getNombreCompleto() : "Deportista #" + contrato.getIdDeportista())
                .disciplinaDeportista(deportista != null ? deportista.getDisciplina() : null)
                .montoTotal(contrato.getMontoTotal())
                .estado(contrato.getEstado())
                .fechaCreacion(contrato.getFechaCreacion())
                .metas(metaRepository.listarPorContrato(contrato.getIdContrato())
                        .stream()
                        .map(this::toMetaResponse)
                        .toList())
                .build();
    }

    private MetaContratoDetalleResponse toMetaResponse(MetaContrato meta) {
        return MetaContratoDetalleResponse.builder()
                .idMeta(meta.getIdMetaContrato())
                .idPlantilla(meta.getIdPlantilla())
                .descripcionAcordada(meta.getDescripcionAcordada())
                .montoDeportista(meta.getMontoDeportista())
                .montoNegocio(meta.getMontoNegocio())
                .comentarioDeportista(meta.getComentarioDeportista())
                .urlEvidencia(meta.getUrlEvidencia())
                .estado(meta.getEstado())
                .build();
    }
}