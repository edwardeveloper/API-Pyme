package com.api.ntc6001.controller;

import com.api.ntc6001.model.dto.EstadisticasPreguntasPorMype;
import com.api.ntc6001.service.impl.GeminiIA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/gemini")
public class GeminiApiController {
    @Autowired(required=true)
    private GeminiIA geminiIA;

    @GetMapping("/{mype}")
    public ResponseEntity<String> requestGemini(@PathVariable Long mype) throws IOException, SQLException {
         geminiIA.geminiGenerate(mype);
        return ResponseEntity.ok("Tareas iniciadas");
    }

//    @GetMapping("/reporte/seccion/{mype}")
//    public ResponseEntity<String> resporteSeccion(@PathVariable Long mype) throws IOException, SQLException {
//
//        return ResponseEntity.ok("Tareas iniciadas");
//    }

//    @PostMapping("/reporte/")
//    public ResponseEntity<String> requestGemini(@RequestBody String[] data) throws IOException, SQLException {
////        return ResponseEntity.ok(geminiIA.reportRRSeccion(Long.parseLong(data[0]),data[1]));
//
//        log.info("HERREEEEEEE:::::"+data[0]);
//        return null;
//    }

    @GetMapping("/reporte/estadisticas/preguntas/mype/{mype}")
    public ResponseEntity<List<EstadisticasPreguntasPorMype>> obtenerEstadisticasPreguntasPorMypeController(@PathVariable Long mype) throws SQLException {
        List<EstadisticasPreguntasPorMype> result = geminiIA.obtenerEstadisticasPreguntasPorMype(mype);
//        log.info(result.toString());
        return ResponseEntity.ok(result);
    }


}
