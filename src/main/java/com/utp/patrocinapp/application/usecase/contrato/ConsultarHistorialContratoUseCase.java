package com.utp.patrocinapp.application.usecase.contrato;

import com.utp.patrocinapp.application.dto.contrato.HistorialContratoResponse;
import com.utp.patrocinapp.application.dto.contrato.TransaccionResponse;
import com.utp.patrocinapp.application.dto.evidencia.EvidenciaResponse;
import com.utp.patrocinapp.application.service.AutorizacionRecursosService;
import com.utp.patrocinapp.domain.model.Contrato;
import com.utp.patrocinapp.domain.ports.input.ConsultarHistorialContratoInputPort;
import com.utp.patrocinapp.domain.ports.output.*;
import com.utp.patrocinapp.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultarHistorialContratoUseCase implements ConsultarHistorialContratoInputPort {
    private final ContratoRepositoryPort contratoRepository;
    private final MetaContratoRepositoryPort metaRepository;
    private final EvidenciaRepositoryPort evidenciaRepository;
    private final TransaccionRepositoryPort transaccionRepository;
    private final UsuarioAutenticadoPort usuarioAutenticadoPort;
    private final AutorizacionRecursosService autorizacion;

    @Override
    public HistorialContratoResponse ejecutar(Integer idContrato) {
        Contrato contrato = contratoRepository.buscarPorId(idContrato)
                .orElseThrow(() -> new ResourceNotFoundException("CONTRATO_INEXISTENTE", "El contrato no existe."));
        autorizacion.validarParticipante(usuarioAutenticadoPort.actual(), contrato);
        var evidencias = metaRepository.listarPorContrato(idContrato).stream()
                .flatMap(meta -> evidenciaRepository.listarPorMeta(meta.getIdMetaContrato()).stream())
                .map(EvidenciaResponse::from).toList();
        var transacciones = transaccionRepository.listarPorContrato(idContrato).stream()
                .map(TransaccionResponse::from).toList();
        return new HistorialContratoResponse(idContrato, evidencias, transacciones);
    }
}
