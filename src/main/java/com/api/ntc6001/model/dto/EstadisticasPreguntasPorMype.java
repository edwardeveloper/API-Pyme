package com.api.ntc6001.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EstadisticasPreguntasPorMype {

//    private Long idpregunta;
//    private String PCapitulo;
//    private String PSeccion;
//    private String PItem;
//    private String PLiteral;
//    private String PTitulo;
//    private String PPregunta;
//    private Long cuestionarioid;
//    private Long pregunta_idpregunta;
//    private Long mype_idmype;
//    private String PPRespuestas;
//    private String PPNotas;
//    private String PPObservaciones;
    private String capitulo;
    private String seccion;
    private int countPregunta;
    private int countCuestionario;
    private int cumple;
    private int cumpleParcialmente;
    private int noCumple;

}
