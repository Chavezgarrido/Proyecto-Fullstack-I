package com.erp.ms_products.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name="products")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    @NotBlank(message = "El SKU es obligatorio")
    private String sku;

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La descripción no puede estar en blanco")
    private String descripcion;

    @Min(value = 1, message = "El precio debe ser superior a $1") 
    private int precio; 

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.fechaActualizacion = LocalDateTime.now();
    }
}
