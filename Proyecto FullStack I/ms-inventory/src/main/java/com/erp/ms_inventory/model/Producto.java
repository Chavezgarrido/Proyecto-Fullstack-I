package com.erp.ms_inventory.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="productos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar en blanco")
    private String nombre;

    @NotBlank(message = "La descripcion no puede estar en blanco")
    private String descripcion;

    @Min(value = 1, message = "El precio debe ser superior a $1") 
    private int precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;
}
