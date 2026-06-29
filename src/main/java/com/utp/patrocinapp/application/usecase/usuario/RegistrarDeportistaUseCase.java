package com.utp.patrocinapp.application.usecase.usuario;

import com.utp.patrocinapp.application.dto.usuario.RegistrarDeportistaRequest;
import com.utp.patrocinapp.application.dto.usuario.RegistrarUsuarioRequest;
import com.utp.patrocinapp.application.dto.usuario.RegistrarUsuarioResponse;
import com.utp.patrocinapp.domain.enums.Rol;
import com.utp.patrocinapp.domain.model.PerfilDeportista;
import com.utp.patrocinapp.domain.model.Usuario;
import com.utp.patrocinapp.domain.ports.input.RegistrarDeportistaInputPort;
import com.utp.patrocinapp.domain.ports.output.PerfilDeportistaRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.UsuarioRepositoryPort;
import com.utp.patrocinapp.domain.service.PasswordEncoderPort;
import com.utp.patrocinapp.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarDeportistaUseCase implements RegistrarDeportistaInputPort {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PerfilDeportistaRepositoryPort perfilDeportistaRepository;
    private final PasswordEncoderPort passwordEncoder;

    @Override
    public RegistrarUsuarioResponse ejecutar(RegistrarDeportistaRequest request) {

        if (usuarioRepository.existePorCorreo(request.getCorreo())) {
            throw new BusinessException("El correo ya se encuentra registrado.");
        }

        Usuario usuario = Usuario.crear(
                request.getCorreo(),
                passwordEncoder.encode(request.getPassword()),
                Rol.DEPORTISTA
        );

        Usuario usuarioGuardado = usuarioRepository.guardar(usuario);

        PerfilDeportista perfil = PerfilDeportista.crear(
                usuarioGuardado,
                request.getDni(),
                request.getDisciplina()
        );

        perfilDeportistaRepository.guardar(perfil);

        return RegistrarUsuarioResponse.builder()
                .id(usuarioGuardado.getId())
                .correo(usuarioGuardado.getCorreo())
                .rol(usuarioGuardado.getRol())
                .build();
    }
}