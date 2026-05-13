package com.erp.ms_orders.dto;
import lombok.Data;

@Data
public class ProductDTO {
    private String sku;
    private String nombre;
    private Double precio;
    private Integer stock;
}
