package com.utp.patrocinapp.application.usecase.auth;

import com.utp.patrocinapp.application.dto.auth.LoginRequest;
import com.utp.patrocinapp.application.dto.auth.LoginResponse;
import com.utp.patrocinapp.domain.model.Usuario;
import com.utp.patrocinapp.domain.ports.input.LoginInputPort;
import com.utp.patrocinapp.domain.ports.output.UsuarioRepositoryPort;
import com.utp.patrocinapp.domain.service.PasswordEncoderPort;
import com.utp.patrocinapp.infrastructure.security.JwtService;
import com.utp.patrocinapp.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.utp.patrocinapp.infrastructure.security.JwtService;

@Service
@RequiredArgsConstructor
public class LoginUseCase implements LoginInputPort {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginResponse ejecutar(LoginRequest request) {

        Usuario usuario = usuarioRepository.buscarPorCorreo(request.getCorreo())
                .orElseThrow(() ->
                        new BusinessException("Correo o contraseña incorrectos.")
                );

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new BusinessException("Correo o contraseña incorrectos.");
        }

        String token = jwtService.generateToken(
                usuario.getCorreo());

        return LoginResponse.builder()
                .id(usuario.getId())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol())
                .token(token)
                .build();
    }
}