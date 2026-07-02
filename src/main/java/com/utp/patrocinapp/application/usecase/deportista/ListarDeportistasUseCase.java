package com.utp.patrocinapp.application.usecase.deportista;

import com.utp.patrocinapp.application.dto.deportista.DeportistaCatalogoResponse;
import com.utp.patrocinapp.domain.model.PerfilDeportista;
import com.utp.patrocinapp.domain.model.PlantillaMeta;
import com.utp.patrocinapp.domain.model.Usuario;
import com.utp.patrocinapp.domain.ports.input.ListarDeportistasInputPort;
import com.utp.patrocinapp.domain.ports.output.ContratoRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.PerfilDeportistaRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.PlantillaMetaRepositoryPort;
import com.utp.patrocinapp.domain.ports.output.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListarDeportistasUseCase implements ListarDeportistasInputPort {

    private final PerfilDeportistaRepositoryPort perfilRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final PlantillaMetaRepositoryPort plantillaRepository;
    private final ContratoRepositoryPort contratoRepository;

    @Override
    public List<DeportistaCatalogoResponse> ejecutar(String busqueda, String disciplina) {
        String filtroBusqueda = normalizar(busqueda);
        String filtroDisciplina = normalizar(disciplina);
        BigDecimal montoObjetivoSugerido = calcularMontoObjetivoSugerido();

        return perfilRepository.listarTodos()
                .stream()
                .filter(perfil -> coincideBusqueda(perfil, filtroBusqueda))
                .filter(perfil -> coincideDisciplina(perfil, filtroDisciplina))
                .map(perfil -> toResponse(perfil, montoObjetivoSugerido))
                .toList();
    }

    private DeportistaCatalogoResponse toResponse(PerfilDeportista perfil,
                                                  BigDecimal montoObjetivoSugerido) {
        Optional<Usuario> usuario = usuarioRepository.buscarPorId(perfil.getIdUsuario());
        long contratosActivos = contratoRepository.buscarPorDeportista(perfil.getIdUsuario()).size();

        return DeportistaCatalogoResponse.builder()
                .idUsuario(perfil.getIdUsuario())
                .correo(usuario.map(Usuario::getCorreo).orElse(null))
                .nombreCompleto(valorORespaldo(perfil.getNombreCompleto(), "Deportista #" + perfil.getIdUsuario()))
                .dni(perfil.getDni())
                .disciplina(perfil.getDisciplina())
                .biografia(valorORespaldo(perfil.getBiografia(), "Perfil deportivo pendiente de completar."))
                .montoObjetivo(montoObjetivoSugerido)
                .contratosActivos(contratosActivos)
                .build();
    }

    private BigDecimal calcularMontoObjetivoSugerido() {
        BigDecimal total = plantillaRepository.listarTodas()
                .stream()
                .map(PlantillaMeta::getPrecioSugerido)
                .filter(precio -> precio != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.compareTo(BigDecimal.ZERO) > 0
                ? total
                : new BigDecimal("4500.00");
    }

    private boolean coincideBusqueda(PerfilDeportista perfil, String filtro) {
        if (filtro.isBlank()) {
            return true;
        }

        String nombre = normalizar(perfil.getNombreCompleto());
        String bio = normalizar(perfil.getBiografia());
        String deporte = normalizar(perfil.getDisciplina());

        return nombre.contains(filtro)
                || bio.contains(filtro)
                || deporte.contains(filtro);
    }

    private boolean coincideDisciplina(PerfilDeportista perfil, String filtro) {
        return filtro.isBlank()
                || "todos".equals(filtro)
                || normalizar(perfil.getDisciplina()).equals(filtro);
    }

    private String normalizar(String valor) {
        return valor == null ? "" : valor.trim().toLowerCase(Locale.ROOT);
    }

    private String valorORespaldo(String valor, String respaldo) {
        return valor == null || valor.isBlank() ? respaldo : valor;
    }
}