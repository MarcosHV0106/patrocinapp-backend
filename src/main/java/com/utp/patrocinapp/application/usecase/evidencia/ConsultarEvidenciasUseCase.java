package com.utp.patrocinapp.application.usecase.evidencia;

import com.utp.patrocinapp.application.dto.evidencia.EvidenciaResponse;
import com.utp.patrocinapp.application.service.AutorizacionRecursosService;
import com.utp.patrocinapp.domain.model.Contrato;
import com.utp.patrocinapp.domain.model.Evidencia;
import com.utp.patrocinapp.domain.model.MetaContrato;
import com.utp.patrocinapp.domain.model.UsuarioAutenticado;
import com.utp.patrocinapp.domain.ports.input.ConsultarEvidenciasInputPort;
import com.utp.patrocinapp.domain.ports.output.EvidenciaRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.MetaContratoRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.UsuarioAutenticadoPort;
import com.utp.patrocinapp.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultarEvidenciasUseCase implements ConsultarEvidenciasInputPort {
    private final EvidenciaRepositoryPort evidenciaRepository;
    private final MetaContratoRepositoryPort metaRepository;
    private final UsuarioAutenticadoPort usuarioAutenticadoPort;
    private final AutorizacionRecursosService autorizacion;

    @Override
    public List<EvidenciaResponse> listarPorMeta(Integer idMeta) {
        validarAccesoMeta(idMeta);
        return evidenciaRepository.listarPorMeta(idMeta).stream().map(EvidenciaResponse::from).toList();
    }

    @Override
    public EvidenciaResponse consultarActual(Integer idMeta) {
        validarAccesoMeta(idMeta);
        return evidenciaRepository.buscarActualPorMeta(idMeta).map(EvidenciaResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("EVIDENCIA_ACTUAL_INEXISTENTE",
                        "No existe una evidencia en revisión para esta meta."));
    }

    @Override
    public EvidenciaResponse consultarPorId(Long idEvidencia) {
        Evidencia evidencia = evidenciaRepository.buscarPorId(idEvidencia)
                .orElseThrow(() -> new ResourceNotFoundException("EVIDENCIA_INEXISTENTE", "La evidencia no existe."));
        validarAccesoMeta(evidencia.getIdMetaContrato());
        return EvidenciaResponse.from(evidencia);
    }

    private void validarAccesoMeta(Integer idMeta) {
        MetaContrato meta = metaRepository.buscarPorId(idMeta)
                .orElseThrow(() -> new ResourceNotFoundException("META_INEXISTENTE", "La meta no existe."));
        Contrato contrato = autorizacion.contratoDeMeta(meta);
        UsuarioAutenticado actor = usuarioAutenticadoPort.actual();
        autorizacion.validarParticipante(actor, contrato);
    }
}
