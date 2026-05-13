package com.erp.ms_orders.client;

import com.erp.ms_orders.dto.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-clients")
public interface ClientFeignClient {

    @GetMapping("/api/clients/rut/{rut}")
    ClientDTO getByRut(@PathVariable("rut") String rut);
}