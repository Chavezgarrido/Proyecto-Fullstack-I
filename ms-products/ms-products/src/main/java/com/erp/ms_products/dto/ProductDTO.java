package com.erp.ms_products.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de transferencia de datos para la gestión e inventario de productos")
public class ProductDTO {

    @Schema(description = "Identificador alfanumérico único generado para el producto", example = "PROD-A39B2", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String id;

    @Schema(description = "Código SKU único de control comercial", example = "TECL-MEC-RGB", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El SKU es obligatorio")
    private String sku;

    @Schema(description = "Nombre comercial o título descriptivo del artículo", example = "Teclado Mecánico HyperX", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Detalles técnicos, características o especificaciones del producto", example = "Teclado con switches Red, distribución ISO en español y retroiluminación RGB.")
    private String descripcion;

    @Schema(description = "Precio unitario neto del artículo (en pesos chilenos)", example = "54990")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private int precio;

    @Schema(description = "Unidades físicas disponibles actualmente en la bodega del ERP", example = "45")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Schema(
        type = "string", 
        pattern = "yyyy-MM-dd'T'HH:mm:ss", 
        example = "2026-06-11T23:45:00", 
        description = "Marca de tiempo de la última modificación del registro en el sistema"
    )
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaActualizacion;

}