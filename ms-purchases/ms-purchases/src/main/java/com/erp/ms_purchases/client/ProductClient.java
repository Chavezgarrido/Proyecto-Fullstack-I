package com.erp.ms_purchases.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-products")
public interface ProductClient {   
    @PostMapping 
    void updateStock(@RequestParam("sku") String sku, @RequestParam("cantidad") int cantidad);
}
