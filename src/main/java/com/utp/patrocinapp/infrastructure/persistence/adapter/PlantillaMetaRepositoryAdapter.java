package com.utp.patrocinapp.infrastructure.persistence.adapter;

import com.utp.patrocinapp.domain.model.PlantillaMeta;
import com.utp.patrocinapp.domain.ports.output.PlantillaMetaRepositoryPort;
import com.utp.patrocinapp.infrastructure.persistence.mapper.PlantillaMetaMapper;
import com.utp.patrocinapp.infrastructure.persistence.repository.PlantillaMetaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlantillaMetaRepositoryAdapter implements PlantillaMetaRepositoryPort {

    private final PlantillaMetaJpaRepository repository;

    @Override
    public List<PlantillaMeta> listarTodas() {

        return repository.findAll()
                .stream()
                .map(PlantillaMetaMapper::toDomain)
                .toList();

    }

}