package com.erp.ms_orders.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductDTO {

    @NotNull(message = "El sku es obligatorio")
    private String sku;
    private String nombre;
    private Double precio;
    private Integer stock;
}
