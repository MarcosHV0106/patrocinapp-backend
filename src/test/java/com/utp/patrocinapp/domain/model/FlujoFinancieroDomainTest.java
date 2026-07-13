package com.utp.patrocinapp.domain.model;

import com.utp.patrocinapp.domain.enums.EstadoEvidencia;
import com.utp.patrocinapp.domain.enums.EstadoMeta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class FlujoFinancieroDomainTest {
    @Test
    void fondoLiberaMontoSinQuedarNegativo() {
        FondoGarantia fondo = FondoGarantia.crear(10, new BigDecimal("330.00"));
        fondo.liberar(new BigDecimal("110.00"));
        assertThat(fondo.getMontoCongelado()).isEqualByComparingTo("220.00");
        assertThat(fondo.getMontoLiberado()).isEqualByComparingTo("110.00");
    }

    @Test
    void fondoRechazaSaldoInsuficiente() {
        FondoGarantia fondo = FondoGarantia.crear(10, new BigDecimal("50.00"));
        assertThatThrownBy(() -> fondo.liberar(new BigDecimal("110.00")))
                .isInstanceOf(IllegalStateException.class);
        assertThat(fondo.getMontoCongelado()).isEqualByComparingTo("50.00");
    }

    @Test
    void metaSigueTransicionesDeRevisionAPago() {
        MetaContrato meta = MetaContrato.crear(1, "Publicación", new BigDecimal("100"),
                new BigDecimal("110"), null);
        meta.marcarEnRevision("Primera entrega");
        meta.marcarAprobada();
        meta.marcarPagada();
        assertThat(meta.getEstado()).isEqualTo(EstadoMeta.PAGADA);
    }

    @Test
    void metaPagadaNoAceptaNuevaEvidencia() {
        MetaContrato meta = MetaContrato.crear(1, "Publicación", new BigDecimal("100"),
                new BigDecimal("110"), null);
        meta.marcarEnRevision(null);
        meta.marcarAprobada();
        meta.marcarPagada();
        assertThatThrownBy(() -> meta.marcarEnRevision("duplicada"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void evidenciaSoloPuedeRevisarseUnaVezYExigeMotivo() {
        Evidencia evidencia = Evidencia.crear(20, 1, "meta.png", "image/png", 128,
                "abc123", "Entrega");
        assertThatThrownBy(() -> evidencia.rechazar("  ", 7))
                .isInstanceOf(IllegalArgumentException.class);
        evidencia.rechazar("Falta visibilidad", 7);
        assertThat(evidencia.getEstado()).isEqualTo(EstadoEvidencia.RECHAZADA);
        assertThat(evidencia.getMotivoRechazo()).isEqualTo("Falta visibilidad");
        assertThatThrownBy(() -> evidencia.aprobar(7)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void evidenciaAdmiteReconstruccionCompletaDesdePersistencia() {
        LocalDateTime ahora = LocalDateTime.now();
        Evidencia evidencia = new Evidencia();
        evidencia.setIdEvidencia(9L);
        evidencia.setIdMetaContrato(20);
        evidencia.setNumeroIntento(2);
        evidencia.setNombreOriginal("evidencia.pdf");
        evidencia.setTipoMime("application/pdf");
        evidencia.setTamanioBytes(512);
        evidencia.setHashSha256("hash");
        evidencia.setComentarioDeportista("comentario");
        evidencia.setEstado(EstadoEvidencia.EN_REVISION);
        evidencia.setMotivoRechazo(null);
        evidencia.setFechaCarga(ahora);
        evidencia.setFechaRevision(null);
        evidencia.setIdUsuarioRevisor(null);
        evidencia.setFechaActualizacion(ahora);
        evidencia.setVersion(3L);

        assertThat(evidencia.getIdEvidencia()).isEqualTo(9L);
        assertThat(evidencia.getIdMetaContrato()).isEqualTo(20);
        assertThat(evidencia.getNumeroIntento()).isEqualTo(2);
        assertThat(evidencia.getNombreOriginal()).isEqualTo("evidencia.pdf");
        assertThat(evidencia.getTipoMime()).isEqualTo("application/pdf");
        assertThat(evidencia.getTamanioBytes()).isEqualTo(512);
        assertThat(evidencia.getHashSha256()).isEqualTo("hash");
        assertThat(evidencia.getComentarioDeportista()).isEqualTo("comentario");
        assertThat(evidencia.getEstado()).isEqualTo(EstadoEvidencia.EN_REVISION);
        assertThat(evidencia.getMotivoRechazo()).isNull();
        assertThat(evidencia.getFechaCarga()).isEqualTo(ahora);
        assertThat(evidencia.getFechaRevision()).isNull();
        assertThat(evidencia.getIdUsuarioRevisor()).isNull();
        assertThat(evidencia.getFechaActualizacion()).isEqualTo(ahora);
        assertThat(evidencia.getVersion()).isEqualTo(3L);
    }
}
