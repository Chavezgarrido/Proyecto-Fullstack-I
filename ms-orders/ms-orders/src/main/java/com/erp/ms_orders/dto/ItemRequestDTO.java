package com.erp.ms_orders.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ItemRequestDTO {

    @NotBlank(message = "El SKU del producto es obligatorio")
    private String productoSku;

    @Min(value = 1, message = "La cantidad mínima es 1")
    @NotNull(message = "La cantidad es obligatoria")
    private Integer cantidad;
}