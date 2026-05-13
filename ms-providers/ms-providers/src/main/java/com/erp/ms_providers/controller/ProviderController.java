package com.erp.ms_providers.controller;

import com.erp.ms_providers.dto.ProviderDTO;
import com.erp.ms_providers.service.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping
    public ResponseEntity<List<ProviderDTO>> getAll(){
        return ResponseEntity.ok(providerService.getAll());
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<ProviderDTO> getByRut(@PathVariable String rut){
        return ResponseEntity.ok(providerService.getByRut(rut));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProviderDTO>> searchByCategory(@PathVariable String categoria){
        return ResponseEntity.ok(providerService.searchByCategory(categoria));
    }

    @PostMapping
    public ResponseEntity<ProviderDTO> create(@Valid @RequestBody ProviderDTO dto){
        return new ResponseEntity<>(providerService.create(dto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProviderDTO> update(@PathVariable Long id, @RequestBody ProviderDTO dto){
        return ResponseEntity.ok(providerService.update(id, dto));
        
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        providerService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
