package com.utp.patrocinapp.domain.ports.output;
import com.utp.patrocinapp.domain.model.PerfilDeportista;
import java.util.Optional;

public interface PerfilDeportistaRepositoryPort {

    PerfilDeportista guardar(PerfilDeportista perfil);

    Optional<PerfilDeportista> buscarPorId(Integer idUsuario);

}