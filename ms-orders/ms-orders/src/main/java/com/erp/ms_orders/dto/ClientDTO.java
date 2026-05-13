package com.erp.ms_orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private boolean activo;
}