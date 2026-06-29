package com.utp.patrocinapp.domain.ports.input;

import com.utp.patrocinapp.application.dto.plantilla.PlantillaMetaResponse;

import java.util.List;

public interface ListarPlantillasInputPort {

    List<PlantillaMetaResponse> ejecutar();

}