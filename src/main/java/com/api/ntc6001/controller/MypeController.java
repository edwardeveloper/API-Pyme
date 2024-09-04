package com.api.ntc6001.controller;

import com.api.ntc6001.model.entity.Mype;
import com.api.ntc6001.model.entity.Users;
import com.api.ntc6001.service.impl.IMypeImpl;
import com.api.ntc6001.service.impl.IUserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mype")
public class MypeController {

    @Autowired
    private IMypeImpl iMype;

    public MypeController(IMypeImpl iMype) {
        this.iMype = iMype;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mype> getMypeId(@PathVariable Long id){
        return ResponseEntity.ok(iMype.findById(id));
    }

    @GetMapping("/mail/{correo}")
    public ResponseEntity<Mype> getMypeCorreo(@PathVariable String correo){
        return ResponseEntity.ok(iMype.findByEmail(correo));
    }

    @PostMapping("/")
    public ResponseEntity<Mype> createMype(@RequestBody Mype pyme){
        Mype pymeFind = iMype.findByEmail(pyme.getMCorreo());
        if(pymeFind != null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(iMype.save(pyme));
    }

    @PutMapping("/")
    public ResponseEntity<Mype> updateMype(@RequestBody Mype pyme){
        Mype pymeActual = iMype.findById(pyme.getIdmype());
        if(pymeActual == null){
            return ResponseEntity.notFound().build();
        }
        pymeActual.setMNit(pyme.getMNit());
        pymeActual.setMCorreo(pyme.getMCorreo());
        pymeActual.setMDireccion(pyme.getMDireccion());
        pymeActual.setMRut(pyme.getMRut());
        pymeActual.setMNit(pyme.getMNit());
        pymeActual.setMTelefono(pyme.getMTelefono());
        pymeActual.setMObjetoSocial(pyme.getMObjetoSocial());
        pymeActual.setMRazonSocial(pyme.getMRazonSocial());
        return ResponseEntity.ok(iMype.save(pymeActual));
    }


}
