package com.erp.ms_report.dto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VentaDTO {
    private Long id;
    private Long productoId;
    private String clienteRut;
    private Integer cantidad;
    private Double precioTotal;
    private LocalDateTime fecha;
}
