package com.erp.ms_products.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "El SKU es obligatorio")
    private String sku;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private int precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    private LocalDateTime fechaActualizacion;

}
