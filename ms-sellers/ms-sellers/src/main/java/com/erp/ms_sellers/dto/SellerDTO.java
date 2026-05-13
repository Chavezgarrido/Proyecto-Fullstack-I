package com.erp.ms_sellers.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {

    private Long id;

    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String apellido;

    @Email(message = "Email inválido")
    private String email;

    @Min(0) @Max(100)
    private Double porcentajeComision;

    private String sucursal;
    private boolean activo;
}
