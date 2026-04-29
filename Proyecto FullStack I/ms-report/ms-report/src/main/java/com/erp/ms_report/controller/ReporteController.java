package com.erp.ms_report.controller;

import com.erp.ms_report.dto.ReporteResumenDTO;
import com.erp.ms_report.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/resumen")
    public ResponseEntity<ReporteResumenDTO> getResumen(){
        return ResponseEntity.ok(reporteService.obtenerResumen());
    }
}
