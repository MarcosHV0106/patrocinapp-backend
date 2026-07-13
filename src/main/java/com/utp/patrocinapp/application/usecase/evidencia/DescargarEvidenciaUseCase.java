package com.utp.patrocinapp.application.usecase.evidencia;

import com.utp.patrocinapp.application.dto.evidencia.ArchivoEvidenciaResponse;
import com.utp.patrocinapp.application.service.AutorizacionRecursosService;
import com.utp.patrocinapp.domain.model.*;
import com.utp.patrocinapp.domain.ports.input.DescargarEvidenciaInputPort;
import com.utp.patrocinapp.domain.ports.output.*;
import com.utp.patrocinapp.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DescargarEvidenciaUseCase implements DescargarEvidenciaInputPort {
    private final EvidenciaRepositoryPort evidenciaRepository;
    private final MetaContratoRepositoryPort metaRepository;
    private final ArchivoEvidenciaStoragePort storagePort;
    private final UsuarioAutenticadoPort usuarioAutenticadoPort;
    private final AutorizacionRecursosService autorizacion;

    @Override
    public ArchivoEvidenciaResponse ejecutar(Long idEvidencia) {
        Evidencia evidencia = evidenciaRepository.buscarPorId(idEvidencia)
                .orElseThrow(() -> new ResourceNotFoundException("EVIDENCIA_INEXISTENTE", "La evidencia no existe."));
        MetaContrato meta = metaRepository.buscarPorId(evidencia.getIdMetaContrato())
                .orElseThrow(() -> new ResourceNotFoundException("META_INEXISTENTE", "La meta no existe."));
        Contrato contrato = autorizacion.contratoDeMeta(meta);
        autorizacion.validarParticipante(usuarioAutenticadoPort.actual(), contrato);
        byte[] contenido = storagePort.obtener(idEvidencia)
                .orElseThrow(() -> new ResourceNotFoundException("ARCHIVO_EVIDENCIA_INEXISTENTE",
                        "El archivo de la evidencia no está disponible."));
        return new ArchivoEvidenciaResponse(evidencia.getNombreOriginal(), evidencia.getTipoMime(), contenido);
    }
}
