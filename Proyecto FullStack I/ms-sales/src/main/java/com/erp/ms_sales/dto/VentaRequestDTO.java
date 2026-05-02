package com.erp.ms_sales.dto;

import lombok.Data;

@Data
public class VentaRequestDTO {
    private Long productoId;
    private String clienteRut;
    private Integer cantidad;
    private Long vendedorId;
}
