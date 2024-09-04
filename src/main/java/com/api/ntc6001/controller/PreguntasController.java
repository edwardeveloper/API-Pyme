package com.api.ntc6001.controller;

import com.api.ntc6001.model.entity.Preguntas;
import com.api.ntc6001.service.impl.IPreguntasImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
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

//    @GetMapping("/capitulo/{capitulo}")
//    public ResponseEntity<List<Preguntas>> getPreguntaByCapitulo(@PathVariable String capitulo){
//        log.info("CAPITULOOOOO:::::: "+capitulo);
//        return ResponseEntity.ok(iPreguntas.getPreguntaByCapitulo(capitulo));
//    }

    @PostMapping("/capitulo")
    public ResponseEntity<List<Preguntas>> getPreguntaByCapitulo(@RequestBody String capitulo){
        log.info("CAPITULOOOOO:::::: "+capitulo);
        return ResponseEntity.ok(iPreguntas.getPreguntaByCapitulo(capitulo));
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Preguntas>> getPreguntas(){
        return ResponseEntity.ok(iPreguntas.getPregunta());
    }

    @GetMapping("/health")
    public String getPreguntaHealth(){
        return "ok";
    }
}
