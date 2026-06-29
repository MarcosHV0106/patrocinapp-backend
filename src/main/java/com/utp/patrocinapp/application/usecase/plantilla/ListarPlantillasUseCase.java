package com.utp.patrocinapp.application.usecase.plantilla;

import com.utp.patrocinapp.application.dto.plantilla.PlantillaMetaResponse;
import com.utp.patrocinapp.domain.model.PlantillaMeta;
import com.utp.patrocinapp.domain.ports.input.ListarPlantillasInputPort;
import com.utp.patrocinapp.domain.ports.output.PlantillaMetaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarPlantillasUseCase implements ListarPlantillasInputPort {

    private final PlantillaMetaRepositoryPort repository;

    @Override
    public List<PlantillaMetaResponse> ejecutar() {

        return repository.listarTodas()
                .stream()
                .map(this::toResponse)
                .toList();

    }

    private PlantillaMetaResponse toResponse(PlantillaMeta plantilla) {

        return PlantillaMetaResponse.builder()
                .id(plantilla.getId())
                .nombreMeta(plantilla.getNombreMeta())
                .descripcionSugerida(plantilla.getDescripcionSugerida())
                .tipoEntregable(plantilla.getTipoEntregable())
                .precioSugerido(plantilla.getPrecioSugerido())
                .build();

    }

}