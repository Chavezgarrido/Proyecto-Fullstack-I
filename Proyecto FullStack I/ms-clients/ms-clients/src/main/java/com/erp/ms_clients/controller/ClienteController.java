package com.erp.ms_clients.controller;

import com.erp.ms_clients.model.Cliente;
import com.erp.ms_clients.service.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public List<Cliente> listar(){
        return clienteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Cliente> obtenerPorRut(@PathVariable String rut){
        return ResponseEntity.ok(clienteService.buscarPorRut(rut));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente crear(@Valid @RequestBody Cliente cliente){
        return clienteService.crear(cliente);
    }

    @DeleteMapping("/rut/{rut}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String rut){
        clienteService.eliminar(rut);
    }
}
