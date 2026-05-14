package com.erp.ms_orders.client;

import com.erp.ms_orders.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-products")
public interface InventoryFeignClient {

    @GetMapping("/api/products/sku/{sku}")
    ProductDTO getBySku(@PathVariable("sku") String sku);

    @PostMapping("/api/products/update-stock")
    void updateStock(@RequestParam("sku") String sku, @RequestParam("cantidad") Integer cantidad);
}