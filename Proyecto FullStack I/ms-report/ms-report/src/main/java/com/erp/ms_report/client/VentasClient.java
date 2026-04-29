package com.erp.ms_report.client;
import com.erp.ms_report.dto.VentaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "ms-sales")
public interface VentasClient {


    @GetMapping("/api/ventas")
    List<VentaDTO> obtenerTodasLasVentas();
}
