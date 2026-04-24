package com.erp.ms_sales.client;
import com.erp.ms_sales.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-clients")
public interface CustomerClient {

    @GetMapping("/api/clientes/rut/{rut}")
    ClienteDTO buscarPorRut(@PathVariable("rut") String rut);

    @GetMapping("/api/clientes/{id}")
    ClienteDTO buscarPorId(@PathVariable("id") Long id);


}
