package main.java.com.erp.ms_sellers.controller;


import com.erp.ms_sellers.model.Vendedor;
import com.erp.ms_sellers.service.VendedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController

@RequestMapping("api/vendedores")

@RequiredArgsConstructor
public class VendedorController {

    
    private final VendedorService vendedorService;

    
    @GetMapping
    public List<Vendedor> listar() {
        
        return vendedorService.obtenerTodos();
    }

   
    @GetMapping("/{id}")
    
    public ResponseEntity<Vendedor> obtenerPorId(@PathVariable Long id) {
      
        return ResponseEntity.ok(vendedorService.obtenerPorId(id));
    }

    
    @GetMapping("/rut/{rut}")
    public ResponseEntity<Vendedor> obtenerPorRut(@PathVariable String rut) {
        
        return ResponseEntity.ok(vendedorService.buscarPorRut(rut));
    }

    
    @PostMapping
  
    @ResponseStatus(HttpStatus.CREATED)
    
    public Vendedor crear(@Valid @RequestBody Vendedor vendedor) {
       
        return vendedorService.crear(vendedor);
    }

   
    @DeleteMapping("/rut/{rut}")
   
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String rut) {
        
        vendedorService.eliminar(rut);
    }
    @PutMapping("/{id}/registrar-venta")
    public Vendedor sumarVentaYBonificacion(@PathVariable Long id) {
        
        return vendedorService.registrarVenta(id);
    }
}