package com.api.ntc6001.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//JPA
@ToString
@Entity
@Table(name = "Pregunta")
public class Preguntas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idpregunta")
    private Long idpregunta;

    @Column(name="pcapitulo")
    private String PCapitulo;
            
    @Column(name="pseccion")
    private String PSeccion;
            
    @Column(name="pitem")
    private String PItem;
    
    @Column(name="pliteral")
    private String PLiteral;
    
    @Column(name="ptitulo")
    private String PTitulo;
    
    @Column(name="ppregunta")
    private String PPregunta;
    
}
