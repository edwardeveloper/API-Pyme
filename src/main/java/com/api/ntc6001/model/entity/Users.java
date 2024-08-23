package com.api.ntc6001.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//JPA
@ToString
@Entity
@Table(name = "User")
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="iduser")
    private Long iduser;

    @Column(name="ucorreo")
    private String UCorreo;

    @Column(name="upassword")
    private String UPassword;

    @Column(name="unombre")
    private String UNombre;

    @Column(name="uapellido")
    private String UApellido;

    @Column(name="utelefono")
    private Long UTelefono;

    @CreationTimestamp
    @Column(name="ufechaCreacion")
    private String UFechaCreacion;

    @UpdateTimestamp
    @Column(name="uultimoAcceso")
    private String UUltimoAcceso;

    @Column(name="uestado")
    private String UEstado;

    @Column(name="utoken")
    private String UToken;

}
