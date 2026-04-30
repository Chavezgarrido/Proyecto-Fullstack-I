package com.erp.ms_providers.controller;

import com.erp.ms_providers.model.Proveedor;
import com.erp.ms_providers.service.ProveedorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @GetMapping
    public List<Proveedor> listar(){
        return proveedorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(proveedorService.obtenerPorId(id));
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Proveedor> obtenerPorRut(@PathVariable String rut){
        return ResponseEntity.ok(proveedorService.buscarPorRut(rut));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Proveedor crear(@Valid @RequestBody Proveedor proveedor){
        return proveedorService.crear(proveedor);
    }

    @DeleteMapping("/rut/{rut}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String rut){
        proveedorService.eliminar(rut);
    }
}
