package com.api.ntc6001.controller;

import com.api.ntc6001.model.entity.Pyme;
import com.api.ntc6001.model.entity.Users;
import com.api.ntc6001.service.impl.IPymeImpl;
import com.api.ntc6001.service.impl.IUserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pyme")
public class PymeController {

    @Autowired
    private IPymeImpl iPyme;

    public PymeController(IPymeImpl iPyme) {
        this.iPyme = iPyme;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pyme> getPymeId(@PathVariable Long id){
        return ResponseEntity.ok(iPyme.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Pyme> createPyme(@RequestBody Pyme pyme){
        Pyme pymeFind = iPyme.findByEmail(pyme.getPCorreo());
        if(pymeFind != null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(iPyme.save(pyme));
    }

    @PutMapping("/")
    public ResponseEntity<Pyme> updatePyme(@RequestBody Pyme pyme){
        Pyme pymeActual = iPyme.findById(pyme.getIdpyme());
        if(pymeActual == null){
            return ResponseEntity.notFound().build();
        }
        pymeActual.setPNit(pyme.getPNit());
        pymeActual.setPCorreo(pyme.getPCorreo());
        pymeActual.setPPassword(pyme.getPPassword());
        pymeActual.setPDireccion(pyme.getPDireccion());
        pymeActual.setPRut(pyme.getPRut());
        pymeActual.setPNit(pyme.getPNit());
        pymeActual.setPTelefono(pyme.getPTelefono());
        pymeActual.setPObjetoSocial(pyme.getPObjetoSocial());
        pymeActual.setPRazonSocial(pyme.getPRazonSocial()); 
        return ResponseEntity.ok(iPyme.save(pymeActual));
    }


}
