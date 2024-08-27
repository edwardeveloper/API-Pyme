package com.api.ntc6001.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.api.ntc6001.service.impl.ICuestionarioImpl;
import org.springframework.web.bind.annotation.*;
import com.api.ntc6001.model.entity.Cuestionario;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/cuestionario")
public class CuestionarioController {

    @Autowired
    private ICuestionarioImpl icuestionario;

//    public CuestionarioController(ICuestionarioImpl cuestionario) {
//        this.icuestionario = cuestionario;
//    }

//    @GetMapping("/{id}")
    @GetMapping("/list")
    public ResponseEntity<List<Cuestionario>> getPreguntas(){
        return ResponseEntity.ok(icuestionario.getCuestionario());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuestionario> getPreguntaId(@PathVariable Integer id){
        return ResponseEntity.ok(icuestionario.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Cuestionario> createCuestionario(@RequestBody Cuestionario cuestionario){

        Cuestionario cuestFind = icuestionario.findByIdPregunta(cuestionario.getPyme_idpyme(),cuestionario.getPregunta_idpregunta());
//        rafEstimacionService.findOne(rafEstimacion.getNmid());
        if(cuestFind != null){
            return ResponseEntity.notFound().build();
        }
//        Cuestionario result = icuestionario.save(cuestionario);
        return ResponseEntity.ok(icuestionario.save(cuestionario));
    }

    @PutMapping("/")
    public ResponseEntity<Cuestionario> updateCuestionario(@RequestBody Cuestionario cuestionario){
        Cuestionario cuestionarioActual = icuestionario.findById(cuestionario.getCuestionarioid());
        if(cuestionarioActual == null){
            return ResponseEntity.notFound().build();
        }
        cuestionarioActual.setPyme_idpyme(cuestionario.getPyme_idpyme());
        cuestionarioActual.setPregunta_idpregunta(cuestionario.getPregunta_idpregunta());
        cuestionarioActual.setPPRespuestas(cuestionario.getPPRespuestas());
        cuestionarioActual.setPPNotas(cuestionario.getPPNotas());
        cuestionarioActual.setPPObservaciones(cuestionario.getPPObservaciones());
        return ResponseEntity.ok(icuestionario.save(cuestionarioActual));
    }


}
