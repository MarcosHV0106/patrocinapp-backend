package com.utp.patrocinapp.application.usecase.usuario;

import com.utp.patrocinapp.application.dto.usuario.RegistrarNegocioRequest;
import com.utp.patrocinapp.application.dto.usuario.RegistrarUsuarioResponse;
import com.utp.patrocinapp.domain.enums.Rol;
import com.utp.patrocinapp.domain.model.PerfilNegocio;
import com.utp.patrocinapp.domain.model.Usuario;
import com.utp.patrocinapp.domain.ports.input.RegistrarNegocioInputPort;
import com.utp.patrocinapp.domain.ports.output.PerfilNegocioRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.UsuarioRepositoryPort;
import com.utp.patrocinapp.domain.service.PasswordEncoderPort;
import com.utp.patrocinapp.shared.exception.BusinessException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarNegocioUseCase implements RegistrarNegocioInputPort {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PerfilNegocioRepositoryPort perfilRepository;
    private final PasswordEncoderPort passwordEncoder;

    @Override
    public RegistrarUsuarioResponse ejecutar(RegistrarNegocioRequest request) {

        if (usuarioRepository.existePorCorreo(request.getCorreo())) {
            throw new BusinessException("El correo ya se encuentra registrado.");
        }

        Usuario usuario = Usuario.crear(
                request.getCorreo(),
                passwordEncoder.encode(request.getPassword()),
                Rol.NEGOCIO
        );

        usuario = usuarioRepository.guardar(usuario);

        perfilRepository.guardar(
                PerfilNegocio.crear(
                        usuario,
                        request.getRuc(),
                        request.getRazonSocial()
                )
        );

        return RegistrarUsuarioResponse.builder()
                .id(usuario.getId())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol())
                .build();

    }

}