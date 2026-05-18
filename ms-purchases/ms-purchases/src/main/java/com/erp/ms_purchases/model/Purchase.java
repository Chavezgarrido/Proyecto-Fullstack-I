package com.erp.ms_purchases.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "El sku es obligatorio")
    private String productSku;

    @Column(nullable = false)
    private String providerRut;

    private Integer cantidad;
    private int precioUnitario;
    private int total;

    @Column(name = "fecha_compra", updatable = false)
    private LocalDateTime fechaCompra;

    @PrePersist
    protected void onCreate() {
        this.fechaCompra = LocalDateTime.now();
    }
}
