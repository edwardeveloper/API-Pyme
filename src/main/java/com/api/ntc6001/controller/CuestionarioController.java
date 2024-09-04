package com.api.ntc6001.controller;

import com.api.ntc6001.model.dto.CuestionarioInformeTotalDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.api.ntc6001.service.impl.ICuestionarioImpl;
import org.springframework.web.bind.annotation.*;
import com.api.ntc6001.model.entity.Cuestionario;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j
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

    @GetMapping("/list/{pregunta}")
    public ResponseEntity<List<Cuestionario>> getShowPregunta(@PathVariable String pregunta){
        return ResponseEntity.ok(icuestionario.findByPregunta(pregunta));
    }
    @GetMapping("/informe/{pyme}")
    public ResponseEntity<List<?>> getShowPregunta(@PathVariable Integer pyme){
        return ResponseEntity.ok(icuestionario.findByIdPreguntaPyme(pyme));
    }
    @GetMapping("/reporte/{pyme}")
    public ResponseEntity<List<?>> getCuestionarioReporte(@PathVariable Integer pyme){
        return ResponseEntity.ok(icuestionario.findByIdPymeReporte(pyme));
    }

    @PostMapping("/")
    public ResponseEntity<Cuestionario> createCuestionario(@RequestBody Cuestionario cuestionario){
        log.info("HERE!!!!!_::::: "+cuestionario);
        Cuestionario cuestFind = icuestionario.findByIdPreguntaPyme( Integer.valueOf(cuestionario.getMype_idmype()),Integer.valueOf(cuestionario.getPregunta_idpregunta()));
        log.info("HERE!!!!!_::::: "+cuestFind);
        if(cuestFind != null){
            cuestFind.setMype_idmype(cuestionario.getMype_idmype());
            cuestFind.setPregunta_idpregunta(cuestionario.getPregunta_idpregunta());
            cuestFind.setPPRespuestas(cuestionario.getPPRespuestas());
            cuestFind.setPPNotas(cuestionario.getPPNotas());
            cuestFind.setPPObservaciones(cuestionario.getPPObservaciones());
            return ResponseEntity.ok(icuestionario.save(cuestFind));
        }
//        return ResponseEntity.ok(null);
        return ResponseEntity.ok(icuestionario.save(cuestionario));
    }

    @PutMapping("/")
    public ResponseEntity<Cuestionario> updateCuestionario(@RequestBody Cuestionario cuestionario){
        Cuestionario cuestionarioActual = icuestionario.findById(cuestionario.getCuestionarioid());
        if(cuestionarioActual == null){
            return ResponseEntity.notFound().build();
        }
        cuestionarioActual.setMype_idmype(cuestionario.getMype_idmype());
        cuestionarioActual.setPregunta_idpregunta(cuestionario.getPregunta_idpregunta());
        cuestionarioActual.setPPRespuestas(cuestionario.getPPRespuestas());
        cuestionarioActual.setPPNotas(cuestionario.getPPNotas());
        cuestionarioActual.setPPObservaciones(cuestionario.getPPObservaciones());
        return ResponseEntity.ok(icuestionario.save(cuestionarioActual));
    }


}
