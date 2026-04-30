package com.erp.ms_sales.controller;

import com.erp.ms_sales.dto.VentaRequestDTO;
import com.erp.ms_sales.model.Venta;
import com.erp.ms_sales.service.VentaService;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {
    private final VentaService ventaService;
    
    @GetMapping
    public ResponseEntity<List<Venta>> listarTodas(){
        return ResponseEntity.ok(ventaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Venta> realizarVenta(@RequestBody VentaRequestDTO request){
        return new ResponseEntity<>(ventaService.procesarVenta(
            request.getProductoId(),
            request.getClienteRut(),
            request.getCantidad()),
            HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}
