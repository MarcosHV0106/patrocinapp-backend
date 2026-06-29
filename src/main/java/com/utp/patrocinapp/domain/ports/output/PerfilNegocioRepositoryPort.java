package com.utp.patrocinapp.domain.ports.output;
import com.utp.patrocinapp.domain.model.PerfilNegocio;
import java.util.Optional;

public interface PerfilNegocioRepositoryPort {

    PerfilNegocio guardar(PerfilNegocio perfil);

    Optional<PerfilNegocio> buscarPorId(Integer idUsuario);

}