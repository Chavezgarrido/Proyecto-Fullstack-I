package com.erp.ms_orders.client;

import com.erp.ms_orders.dto.SellerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-sellers")
public interface SellerFeignClient {

    @GetMapping("/api/sellers/rut/{rut}")
    SellerDTO getByRut(@PathVariable("rut") String rut);
}
