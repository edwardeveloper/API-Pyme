package com.api.ntc6001.model.dto;

import lombok.Data;

@Data
public class RegisterUserDto {

        private String PRazonSocial;

        private String PDireccion;

        private Long PTelefono;

        private String PNit;

        private String PRut;

        private String PCorreo;

        private String PPassword;

        private String PObjetoSocial;

        private String PRepresentanteLegal;

}
