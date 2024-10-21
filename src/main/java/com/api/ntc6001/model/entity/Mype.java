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
@Table(name = "Mype")

public class Mype implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idmype")
    private Long idmype;

    @Column(name="mrazonsocial")
    private String MRazonSocial;

    @Column(name="mdescripcionempresa")
    private String MDescripcionEmpresa;

    @Column(name="mdireccion")
    private String MDireccion;

    @Column(name="mtelefono")
    private Long MTelefono;

    @Column(name="mnit")
    private int MNit;

    @Column(name="mrut")
    private String MRut;

    @Column(name="mcorreo")
    private String MCorreo;

    @Column(name="mobjetosocial")
    private String MObjetoSocial;

    @Column(name="mrepresentantelegal")
    private String MRepresentanteLegal;

    @Column(name="mtipoempresa")
    private String MTipoEmpresa;

    @Column(name="msector")
    private String MSector;

    @Column(name="user_iduser")
    private Long user_iduser;

}
