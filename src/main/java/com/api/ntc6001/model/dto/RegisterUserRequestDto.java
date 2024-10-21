package com.api.ntc6001.model.dto;

import lombok.Data;

@Data
public class RegisterUserRequestDto {

    private String razonsocial;

    private String descripcionempresa;

    private int nit;

    private String tipoempresa;

    private String sector;

    private String direccion;

    private Long telefono;

    private String correo;

    private String password;

    private String rol;

}
