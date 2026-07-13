package com.utp.patrocinapp.application.usecase.notificacion;

import com.utp.patrocinapp.application.dto.notificacion.NotificacionResponse;
import com.utp.patrocinapp.domain.model.Notificacion;
import com.utp.patrocinapp.domain.model.UsuarioAutenticado;
import com.utp.patrocinapp.domain.ports.input.ConsultarNotificacionesInputPort;
import com.utp.patrocinapp.domain.ports.output.NotificacionRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.UsuarioAutenticadoPort;
import com.utp.patrocinapp.shared.exception.BusinessException;
import com.utp.patrocinapp.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultarNotificacionesUseCase implements ConsultarNotificacionesInputPort {
    private final NotificacionRepositoryPort repository;
    private final UsuarioAutenticadoPort usuarioAutenticadoPort;

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionResponse> listar() {
        return repository.listarPorUsuario(usuarioAutenticadoPort.actual().id()).stream()
                .map(NotificacionResponse::from).toList();
    }

    @Override
    @Transactional
    public NotificacionResponse marcarLeida(Long idNotificacion) {
        UsuarioAutenticado actor = usuarioAutenticadoPort.actual();
        Notificacion notificacion = repository.buscarPorId(idNotificacion)
                .orElseThrow(() -> new ResourceNotFoundException("NOTIFICACION_INEXISTENTE",
                        "La notificación no existe."));
        if (!actor.id().equals(notificacion.getIdUsuario())) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "RECURSO_AJENO",
                    "No puedes modificar una notificación ajena.");
        }
        notificacion.marcarLeida();
        return NotificacionResponse.from(repository.guardar(notificacion));
    }
}
