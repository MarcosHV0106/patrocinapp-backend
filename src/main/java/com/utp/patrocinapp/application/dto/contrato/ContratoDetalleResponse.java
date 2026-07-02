package com.utp.patrocinapp.application.dto.contrato;

import com.utp.patrocinapp.domain.enums.EstadoContrato;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ContratoDetalleResponse {

    private Integer idContrato;
    private Integer idNegocio;
    private String razonSocialNegocio;
    private Integer idDeportista;
    private String nombreDeportista;
    private String disciplinaDeportista;
    private BigDecimal montoTotal;
    private EstadoContrato estado;
    private LocalDateTime fechaCreacion;
    private List<MetaContratoDetalleResponse> metas;

}