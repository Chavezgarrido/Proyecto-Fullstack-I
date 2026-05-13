package com.erp.ms_sellers.controller;

import com.erp.ms_sellers.dto.SellerDTO;
import com.erp.ms_sellers.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<SellerDTO>> getAll() {
        return ResponseEntity.ok(sellerService.getAll());
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<SellerDTO> getByRut(@PathVariable String rut) {
        return ResponseEntity.ok(sellerService.getByRut(rut));
    }

    @PostMapping
    public ResponseEntity<SellerDTO> create(@Valid @RequestBody SellerDTO dto) {
        return new ResponseEntity<>(sellerService.create(dto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SellerDTO> update(@PathVariable Long id, @RequestBody SellerDTO dto) {
        return ResponseEntity.ok(sellerService.update(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeState(@PathVariable Long id, @RequestParam boolean activo) {
        sellerService.changeState(id, activo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sellerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
