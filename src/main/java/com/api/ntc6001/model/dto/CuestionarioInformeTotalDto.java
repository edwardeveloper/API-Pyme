package com.api.ntc6001.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CuestionarioInformeTotalDto  {
    private String capitulo;
    private String seccion;
    private Integer countPregunta;
    private Integer countCuestionario;
    private Integer cumple;
    private Integer cumpleParcialmente;
    private Integer noCumple;
}
