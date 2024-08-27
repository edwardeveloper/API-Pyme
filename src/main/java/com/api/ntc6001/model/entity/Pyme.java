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
@Table(name = "Pyme")

public class Pyme implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idpyme")
    private Long idpyme;

    @Column(name="prazonsocial")
    private String PRazonSocial;

    @Column(name="pdireccion")
    private String PDireccion;

    @Column(name="ptelefono")
    private Long PTelefono;

    @Column(name="pnit")
    private String PNit;

    @Column(name="prut")
    private String PRut;

    @Column(name="pcorreo")
    private String PCorreo;

    @Column(name="ppassword")
    private String PPassword;

    @Column(name="pobjetosocial")
    private String PObjetoSocial;

    @Column(name="prepresentantelegal")
    private String PRepresentanteLegal;

}
