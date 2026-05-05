package com.erp.ms_sales.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "ms-sellers")
public interface SellerClient {
    
    @PostMapping("/sellers/sumar-venta") 
    void sumarVentaYBonificacion(@RequestBody Object vendedorId);

    @PutMapping("/api/vendedores/{id}/registrar-venta")
    void sumarVentaYBonificacion1(@PathVariable("id") Object vendedorId);

}