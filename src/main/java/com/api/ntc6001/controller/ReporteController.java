package com.api.ntc6001.controller;

import com.api.ntc6001.model.entity.Reporte;
import com.api.ntc6001.service.impl.IReporteImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reporte")
public class ReporteController {

    @Autowired
    private IReporteImpl iReporte;

    public ReporteController(IReporteImpl iReporte) {
        this.iReporte = iReporte;
    }

    @GetMapping("/{mype}")
    public ResponseEntity<List<Reporte>> getReporteMype(@PathVariable Long mype){
//        log.info("HEREEEE!!!:::::"+iReporte.findByMype(mype));
        return ResponseEntity.ok(iReporte.findByMype(mype));
    }
}
