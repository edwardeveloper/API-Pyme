package com.api.ntc6001.model.dto;

import lombok.Data;

@Data
public class ReporteDto {

    private Long idreporte;
    private Long mype_idmype;
    private Long RVersion;
    private String RCapitulo;
    private String RRCapitulo;
    private String RSeccion;
    private String RRSeccion;
    private String RConclusiones;
    private String RRecomendaciones;

}
