package com.erp.ms_purchases.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.ms_purchases.dto.PurchaseRequestDTO;
import com.erp.ms_purchases.model.Purchase;
import com.erp.ms_purchases.service.PurchaseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<List<Purchase>> getAll() {
        return ResponseEntity.ok(purchaseService.getAll());
    }

    @PostMapping
    public ResponseEntity<Purchase> realizarCompra(@RequestBody PurchaseRequestDTO dto) {
        return new ResponseEntity<>(purchaseService.processPurchase(dto), HttpStatus.CREATED);
    }
}
