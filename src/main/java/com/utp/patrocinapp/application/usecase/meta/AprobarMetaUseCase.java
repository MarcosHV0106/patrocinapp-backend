package com.utp.patrocinapp.application.usecase.meta;

import com.utp.patrocinapp.application.dto.evidencia.AprobarEvidenciaResponse;
import com.utp.patrocinapp.application.dto.meta.AprobarMetaResponse;
import com.utp.patrocinapp.domain.ports.input.AprobarEvidenciaInputPort;
import com.utp.patrocinapp.domain.ports.input.AprobarMetaInputPort;
import com.utp.patrocinapp.domain.ports.output.EvidenciaRepositoryPort;
import com.utp.patrocinapp.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Compatibilidad temporal. La API principal aprueba una evidencia concreta. */
@Deprecated
@Service
@RequiredArgsConstructor
public class AprobarMetaUseCase implements AprobarMetaInputPort {
    private final EvidenciaRepositoryPort evidenciaRepository;
    private final AprobarEvidenciaInputPort aprobarEvidencia;

    @Override
    public AprobarMetaResponse ejecutar(Integer idMeta) {
        Long idEvidencia = evidenciaRepository.buscarActualPorMeta(idMeta)
                .orElseThrow(() -> new ResourceNotFoundException("EVIDENCIA_ACTUAL_INEXISTENTE",
                        "No existe una evidencia en revisión para la meta."))
                .getIdEvidencia();
        AprobarEvidenciaResponse response = aprobarEvidencia.ejecutar(idEvidencia);
        return AprobarMetaResponse.builder()
                .idMeta(response.idMeta())
                .estado(response.estadoMeta())
                .montoLiberado(response.montoNeto())
                .build();
    }
}
