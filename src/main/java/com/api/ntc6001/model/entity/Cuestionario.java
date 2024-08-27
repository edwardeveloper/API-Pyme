package com.api.ntc6001.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//JPA
@ToString
@Entity
@Table(name = "Cuestionario")
public class Cuestionario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cuestionarioid")
    private Integer cuestionarioid;

    @Column(name="pyme_idpyme")
    private Integer pyme_idpyme;

    @Column(name="pregunta_idpregunta")
    private Integer pregunta_idpregunta;

    @Column(name="pprespuestas")
    private String PPRespuestas;

    @Column(name="ppnotas")
    private String PPNotas;

    @Column(name="ppobservaciones")
    private String PPObservaciones;
}
