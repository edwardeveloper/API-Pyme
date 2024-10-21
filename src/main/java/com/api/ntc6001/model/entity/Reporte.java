package com.api.ntc6001.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="reporte")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idreporte")
    private Long idReporte;

    @Column(name="mype_idmype")
    private Long mype_idmype;

    @Column(name="rversion")
    private Long RVersion;

    @Column(name="rcapitulo")
    private String RCapitulo;

    @Column(name="rrcapitulo")
    private String RRCapitulo;

    @Column(name="rseccion")
    private String RSeccion;

    @Column(name="rrseccion")
    private String RRSeccion;

    @Column(name="rconclusiones")
    private String RConclusiones;

    @Column(name="rrecomendaciones")
    private String RRecomendaciones;

}
