package com.erp.ms_products.controller;

import com.erp.ms_products.dto.ProductDTO;
import com.erp.ms_products.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductDTO> getBySku(@PathVariable String sku){
        return ResponseEntity.ok(productService.getBySku(sku));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable String id){
        return ResponseEntity.ok(productService.getById(id));

    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO dto){
        return new ResponseEntity<>(productService.create(dto), HttpStatus.CREATED);
    }

    @PostMapping("/update-stock")
    public ResponseEntity<ProductDTO> updateStock(@RequestParam String sku, @RequestParam int cantidad){
        return ResponseEntity.ok(productService.updateStock(sku, cantidad));
    }

    @PostMapping("/add-stock")
    public ResponseEntity<ProductDTO> addStock(@RequestParam String sku, @RequestParam int cantidad){
        return ResponseEntity.ok(productService.addStock(sku, cantidad));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid@RequestBody ProductDTO dto){
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
