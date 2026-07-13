package com.utp.patrocinapp.domain.model;

import com.utp.patrocinapp.domain.enums.EstadoEvidencia;

import java.time.LocalDateTime;

public class Evidencia {

    private Long idEvidencia;
    private Integer idMetaContrato;
    private int numeroIntento;
    private String nombreOriginal;
    private String tipoMime;
    private long tamanioBytes;
    private String hashSha256;
    private String comentarioDeportista;
    private EstadoEvidencia estado;
    private String motivoRechazo;
    private LocalDateTime fechaCarga;
    private LocalDateTime fechaRevision;
    private Integer idUsuarioRevisor;
    private LocalDateTime fechaActualizacion;
    private Long version;

    public Evidencia() {
    }

    public Evidencia(Long idEvidencia, Integer idMetaContrato, int numeroIntento,
                     String nombreOriginal, String tipoMime, long tamanioBytes,
                     String hashSha256, String comentarioDeportista, EstadoEvidencia estado,
                     String motivoRechazo, LocalDateTime fechaCarga, LocalDateTime fechaRevision,
                     Integer idUsuarioRevisor, LocalDateTime fechaActualizacion, Long version) {
        this.idEvidencia = idEvidencia;
        this.idMetaContrato = idMetaContrato;
        this.numeroIntento = numeroIntento;
        this.nombreOriginal = nombreOriginal;
        this.tipoMime = tipoMime;
        this.tamanioBytes = tamanioBytes;
        this.hashSha256 = hashSha256;
        this.comentarioDeportista = comentarioDeportista;
        this.estado = estado;
        this.motivoRechazo = motivoRechazo;
        this.fechaCarga = fechaCarga;
        this.fechaRevision = fechaRevision;
        this.idUsuarioRevisor = idUsuarioRevisor;
        this.fechaActualizacion = fechaActualizacion;
        this.version = version;
    }

    public static Evidencia crear(Integer idMetaContrato, int numeroIntento,
                                   String nombreOriginal, String tipoMime, long tamanioBytes,
                                   String hashSha256, String comentarioDeportista) {
        LocalDateTime ahora = LocalDateTime.now();
        return new Evidencia(null, idMetaContrato, numeroIntento, nombreOriginal, tipoMime,
                tamanioBytes, hashSha256, comentarioDeportista, EstadoEvidencia.EN_REVISION,
                null, ahora, null, null, ahora, 0L);
    }

    public void rechazar(String motivo, Integer idRevisor) {
        if (estado != EstadoEvidencia.EN_REVISION) {
            throw new IllegalStateException("Solo una evidencia en revisión puede rechazarse.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("El motivo de rechazo es obligatorio.");
        }
        this.estado = EstadoEvidencia.RECHAZADA;
        this.motivoRechazo = motivo.trim();
        this.idUsuarioRevisor = idRevisor;
        this.fechaRevision = LocalDateTime.now();
        this.fechaActualizacion = this.fechaRevision;
    }

    public void aprobar(Integer idRevisor) {
        if (estado != EstadoEvidencia.EN_REVISION) {
            throw new IllegalStateException("Solo una evidencia en revisión puede aprobarse.");
        }
        this.estado = EstadoEvidencia.APROBADA;
        this.motivoRechazo = null;
        this.idUsuarioRevisor = idRevisor;
        this.fechaRevision = LocalDateTime.now();
        this.fechaActualizacion = this.fechaRevision;
    }

    public Long getIdEvidencia() { return idEvidencia; }
    public void setIdEvidencia(Long idEvidencia) { this.idEvidencia = idEvidencia; }
    public Integer getIdMetaContrato() { return idMetaContrato; }
    public void setIdMetaContrato(Integer idMetaContrato) { this.idMetaContrato = idMetaContrato; }
    public int getNumeroIntento() { return numeroIntento; }
    public void setNumeroIntento(int numeroIntento) { this.numeroIntento = numeroIntento; }
    public String getNombreOriginal() { return nombreOriginal; }
    public void setNombreOriginal(String nombreOriginal) { this.nombreOriginal = nombreOriginal; }
    public String getTipoMime() { return tipoMime; }
    public void setTipoMime(String tipoMime) { this.tipoMime = tipoMime; }
    public long getTamanioBytes() { return tamanioBytes; }
    public void setTamanioBytes(long tamanioBytes) { this.tamanioBytes = tamanioBytes; }
    public String getHashSha256() { return hashSha256; }
    public void setHashSha256(String hashSha256) { this.hashSha256 = hashSha256; }
    public String getComentarioDeportista() { return comentarioDeportista; }
    public void setComentarioDeportista(String comentarioDeportista) { this.comentarioDeportista = comentarioDeportista; }
    public EstadoEvidencia getEstado() { return estado; }
    public void setEstado(EstadoEvidencia estado) { this.estado = estado; }
    public String getMotivoRechazo() { return motivoRechazo; }
    public void setMotivoRechazo(String motivoRechazo) { this.motivoRechazo = motivoRechazo; }
    public LocalDateTime getFechaCarga() { return fechaCarga; }
    public void setFechaCarga(LocalDateTime fechaCarga) { this.fechaCarga = fechaCarga; }
    public LocalDateTime getFechaRevision() { return fechaRevision; }
    public void setFechaRevision(LocalDateTime fechaRevision) { this.fechaRevision = fechaRevision; }
    public Integer getIdUsuarioRevisor() { return idUsuarioRevisor; }
    public void setIdUsuarioRevisor(Integer idUsuarioRevisor) { this.idUsuarioRevisor = idUsuarioRevisor; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}
