package com.erp.ms_sales.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String email;
}
