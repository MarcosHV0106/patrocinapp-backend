package com.utp.patrocinapp.application.dto.deportista;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class DeportistaCatalogoResponse {

    private Integer idUsuario;
    private String correo;
    private String nombreCompleto;
    private String dni;
    private String disciplina;
    private String biografia;
    private BigDecimal montoObjetivo;
    private long contratosActivos;

}