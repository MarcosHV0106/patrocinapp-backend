package com.utp.patrocinapp.domain.ports.output;
import com.utp.patrocinapp.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {

    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorId(Integer id);

    Optional<Usuario> buscarPorCorreo(String correo);

    boolean existePorCorreo(String correo);

}