package com.erp.ms_purchases.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PurchaseRequestDTO {

    @NotNull(message = "El sku es obligatorio")
    private String productSku;

    @NotNull(message = "El proveedor es obligatorio")
    private String providerRut;

    @Min(value = 1)
    private Integer cantidad;

    @Min(value = 1)
    private int precioUnitario;
}
