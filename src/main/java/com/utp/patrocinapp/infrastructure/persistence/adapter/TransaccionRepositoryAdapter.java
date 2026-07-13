package com.utp.patrocinapp.infrastructure.persistence.adapter;

import com.utp.patrocinapp.domain.model.Transaccion;
import com.utp.patrocinapp.domain.ports.output.TransaccionRepositoryPort;
import com.utp.patrocinapp.infrastructure.persistence.mapper.TransaccionMapper;
import com.utp.patrocinapp.infrastructure.persistence.repository.TransaccionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransaccionRepositoryAdapter
        implements TransaccionRepositoryPort {

    private final TransaccionJpaRepository repository;

    @Override
    public Transaccion guardar(Transaccion transaccion) {

        return TransaccionMapper.toDomain(
                repository.save(
                        TransaccionMapper.toEntity(transaccion)
                )
        );

    }

    @Override
    public boolean existePorMeta(Integer idMetaContrato) {
        return repository.existsByIdMetaContrato(idMetaContrato);
    }

    @Override
    public java.util.List<Transaccion> listarPorContrato(Integer idContrato) {
        return repository.findByIdContratoOrderByFechaEjecucionDesc(idContrato).stream()
                .map(TransaccionMapper::toDomain).toList();
    }

}
