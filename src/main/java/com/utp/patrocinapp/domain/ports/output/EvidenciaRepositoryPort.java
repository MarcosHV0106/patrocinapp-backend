package com.utp.patrocinapp.domain.ports.output;

import com.utp.patrocinapp.domain.enums.EstadoEvidencia;
import com.utp.patrocinapp.domain.model.Evidencia;

import java.util.List;
import java.util.Optional;

public interface EvidenciaRepositoryPort {
    Evidencia guardar(Evidencia evidencia);
    Optional<Evidencia> buscarPorId(Long idEvidencia);
    Optional<Evidencia> buscarPorIdParaActualizar(Long idEvidencia);
    List<Evidencia> listarPorMeta(Integer idMetaContrato);
    Optional<Evidencia> buscarActualPorMeta(Integer idMetaContrato);
    long contarPorMeta(Integer idMetaContrato);
    boolean existeHashParaMeta(Integer idMetaContrato, String hashSha256);
    boolean existePorMetaYEstado(Integer idMetaContrato, EstadoEvidencia estado);
}
