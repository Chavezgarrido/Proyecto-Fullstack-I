package com.erp.ms_sellers.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String rut;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String email;

    private Double porcentajeComision; 
    private String sucursal;
    private boolean activo = true;

    @Column(name = "fecha_contratacion", updatable = false)
    private LocalDateTime fechaContratacion;

    @PrePersist
    protected void onCreate() {
        this.fechaContratacion = LocalDateTime.now();
    }
}
