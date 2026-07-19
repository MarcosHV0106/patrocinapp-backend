package com.utp.patrocinapp.infrastructure.security;

import com.utp.patrocinapp.domain.model.Usuario;
import com.utp.patrocinapp.domain.model.UsuarioAutenticado;
import com.utp.patrocinapp.domain.ports.output.UsuarioAutenticadoPort;
import com.utp.patrocinapp.domain.ports.output.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringSecurityUsuarioAutenticadoAdapter implements UsuarioAutenticadoPort {
    private final UsuarioRepositoryPort usuarioRepository;

    @Override
    public UsuarioAutenticado actual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new AccessDeniedException("Se requiere una sesión válida.");
        }

        Usuario usuario = usuarioRepository.buscarPorCorreo(authentication.getName())
                .orElseThrow(() -> new AccessDeniedException("El usuario autenticado ya no existe."));
        return new UsuarioAutenticado(usuario.getId(), usuario.getCorreo(), usuario.getRol());
    }
}
