package com.utp.patrocinapp.domain.ports.output;

import com.utp.patrocinapp.domain.model.PlantillaMeta;

import java.util.List;

public interface PlantillaMetaRepositoryPort {

    List<PlantillaMeta> listarTodas();

}