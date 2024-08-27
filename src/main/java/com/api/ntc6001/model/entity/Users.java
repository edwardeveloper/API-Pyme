package com.api.ntc6001.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//JPA
@ToString
@Entity
@Table(name = "User")
public class Users implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
