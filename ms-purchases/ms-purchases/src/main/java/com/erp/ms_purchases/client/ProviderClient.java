package com.erp.ms_purchases.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.erp.ms_purchases.dto.ProviderDTO;

@FeignClient(name = "ms-providers")
public interface ProviderClient {
    @GetMapping("/api/providers/rut/{rut}")
    ProviderDTO getByRut(@PathVariable("rut") String rut);

}
