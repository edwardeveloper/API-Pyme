package com.api.ntc6001.controller;

import com.api.ntc6001.model.entity.Preguntas;
import com.api.ntc6001.service.impl.IPreguntasImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/preguntas")
public class PreguntasController {

    @Autowired
    private IPreguntasImpl iPreguntas;

    public PreguntasController(IPreguntasImpl iPreguntas) {
        this.iPreguntas = iPreguntas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Preguntas> getPreguntaId(@PathVariable Long id){
        return ResponseEntity.ok(iPreguntas.findById(id));
    }

    @GetMapping("/health")
    public String getPreguntaHealth(){
        return "ok";
    }
}
