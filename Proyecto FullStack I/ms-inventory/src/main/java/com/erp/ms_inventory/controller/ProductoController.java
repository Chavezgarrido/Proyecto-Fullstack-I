package com.erp.ms_inventory.controller;

import com.erp.ms_inventory.model.Producto;
import com.erp.ms_inventory.service.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<Producto> listar(){
        return productoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @GetMapping("/buscar")
    public List<Producto> buscarPorNombre(@RequestParam String nombre){
        return productoService.buscarPorNombre(nombre);
    }

    @GetMapping("/stock-bajo")
    public List<Producto> alertaStock(@RequestParam Integer limite){
        return productoService.obtenerStockBajo(limite);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@Valid @RequestBody Producto producto){
        return productoService.guardar(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto){
        return productoService.actualizar(id, producto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        productoService.eliminar(id);
    }

    @PutMapping("/{id}/stock-aumentar")
    public Producto sumarStock(@PathVariable Long id, @RequestParam Integer cantidad){
        return productoService.aumentarStock(id, cantidad);
    }

    @PutMapping("/{id}/stock-restar")
    public Producto restarStock(@PathVariable Long id, @RequestParam Integer cantidad){
        return productoService.restarStock(id, cantidad);
    }
}
