package com.erp.ms_sales.client;

import com.erp.ms_sales.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-inventory")
public interface InventoryClient {

    @GetMapping("/api/productos/{id}")
    ProductoDTO getProductoById(@PathVariable("id") Long id);

    @PutMapping("/api/productos/{id}/stock-restar")
    void restarStock(
        @PathVariable("id") Long id, 
        @RequestParam("cantidad") Integer cantidad
    );
}
