package com.api.ntc6001.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
//JPA
@ToString
@Entity
@Table(name = "Pyme")

public class Pyme implements Serializable, UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
