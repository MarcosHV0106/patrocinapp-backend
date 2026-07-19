package com.utp.patrocinapp.application.service;

import com.utp.patrocinapp.domain.enums.Rol;
import com.utp.patrocinapp.domain.model.Contrato;
import com.utp.patrocinapp.domain.model.MetaContrato;
import com.utp.patrocinapp.domain.model.UsuarioAutenticado;
import com.utp.patrocinapp.domain.ports.output.ContratoRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.UsuarioAutenticadoPort;
import com.utp.patrocinapp.shared.exception.BusinessException;
import com.utp.patrocinapp.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutorizacionRecursosService {
    private final UsuarioAutenticadoPort usuarioAutenticadoPort;
    private final ContratoRepositoryPort contratoRepository;

    public UsuarioAutenticado actorConRol(Rol rol) {
        UsuarioAutenticado actor = usuarioAutenticadoPort.actual();
        if (!actor.tieneRol(rol)) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "ROL_INCORRECTO",
                    "Tu cuenta no tiene el rol requerido para esta operación.");
        }
        return actor;
    }

    public Contrato contratoDeMeta(MetaContrato meta) {
        return contratoRepository.buscarPorId(meta.getIdContrato())
                .orElseThrow(() -> new ResourceNotFoundException("CONTRATO_INEXISTENTE",
                        "El contrato asociado no existe."));
    }

    public void validarDeportista(UsuarioAutenticado actor, Contrato contrato) {
        if (!actor.tieneRol(Rol.DEPORTISTA) || !actor.id().equals(contrato.getIdDeportista())) {
            throw recursoAjeno();
        }
    }

    public void validarNegocio(UsuarioAutenticado actor, Contrato contrato) {
        if (!actor.tieneRol(Rol.NEGOCIO) || !actor.id().equals(contrato.getIdNegocio())) {
            throw recursoAjeno();
        }
    }

    public void validarParticipante(UsuarioAutenticado actor, Contrato contrato) {
        boolean participa = actor.id().equals(contrato.getIdNegocio())
                || actor.id().equals(contrato.getIdDeportista());
        if (!participa) throw recursoAjeno();
    }

    private BusinessException recursoAjeno() {
        return new BusinessException(HttpStatus.FORBIDDEN, "RECURSO_AJENO",
                "No tienes permiso para acceder a este recurso.");
    }
}
