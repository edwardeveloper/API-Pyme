package com.api.ntc6001.model.entity;


//import com.api.ntc6001.util.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;

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
    @Column(name="ufechacreacion")
    private String UFechaCreacion;

    @UpdateTimestamp
    @Column(name="uultimoacceso")
    private String UUltimoAcceso;

    @Column(name="uestado")
    private String UEstado;
//    @Enumerated(EnumType.STRING)
//    @Column(name="urole")
//    private Role URole;

    @Column(name="urole")
    private String URole;

    @Column(name="utoken")
    private String UToken;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = URole.getPermissions().stream().map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
//                .collect(Collectors.toList());;
//
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + URole.name()));
//
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return UPassword;
//    }
//
//    @Override
//    public String getUsername() {
//        return UNombre;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

}
