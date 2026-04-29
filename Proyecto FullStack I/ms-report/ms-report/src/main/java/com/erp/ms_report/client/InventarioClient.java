package com.erp.ms_report.client;

import com.erp.ms_report.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "ms-inventory")
public interface InventarioClient {

    @GetMapping("/api/productos")
    List<ProductoDTO> obtenerTodosLosProductos();

}
