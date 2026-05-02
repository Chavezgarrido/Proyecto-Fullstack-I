package com.erp.ms_sales.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name = "ms-sellers")
public interface SellerClient {
    
    
    @PutMapping("/api/vendedores/{id}/registrar-venta")
    void sumarVentaYBonificacion1(@PathVariable("id") Object vendedorId);

    void sumarVentaYBonificacion(Object vendedorId);
}