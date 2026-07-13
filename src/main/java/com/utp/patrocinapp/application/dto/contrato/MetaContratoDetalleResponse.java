package com.utp.patrocinapp.application.dto.contrato;

import com.utp.patrocinapp.domain.enums.EstadoMeta;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import com.utp.patrocinapp.application.dto.evidencia.EvidenciaResponse;

@Getter
@Builder
public class MetaContratoDetalleResponse {

    private Integer idMeta;
    private Integer idPlantilla;
    private String descripcionAcordada;
    private BigDecimal montoDeportista;
    private BigDecimal montoNegocio;
    private String comentarioDeportista;
    private String urlEvidencia;
    private EstadoMeta estado;
    private EvidenciaResponse evidenciaActual;
    private List<EvidenciaResponse> evidencias;

}
