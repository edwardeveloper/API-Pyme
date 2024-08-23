package com.api.ntc6001.controller;

import com.api.ntc6001.model.entity.Users;
import com.api.ntc6001.service.impl.IUserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private IUserImpl iUser;

    public UsersController(IUserImpl iUser) {
        this.iUser = iUser;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getPreguntaId(@PathVariable Long id){
        return ResponseEntity.ok(iUser.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Users> createUser(@RequestBody Users users){
        Users usersFind = iUser.findByEmail(users.getUCorreo());
        if(usersFind != null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(iUser.save(users));
    }

    @PutMapping("/")
    public ResponseEntity<Users> updateUser(@RequestBody Users users){
        Users usersActual = iUser.findById(users.getIduser());
        if(usersActual == null){
            return ResponseEntity.notFound().build();
        }
        usersActual.setUNombre(users.getUNombre());
        usersActual.setUApellido(users.getUApellido());
        usersActual.setUCorreo(users.getUCorreo());
        usersActual.setUPassword(users.getUPassword());
        usersActual.setUEstado(users.getUEstado());
        usersActual.setUTelefono(users.getUTelefono());
        usersActual.setUFechaCreacion(users.getUFechaCreacion());
        usersActual.setUToken(users.getUToken());
        return ResponseEntity.ok(iUser.save(usersActual));
    }
}