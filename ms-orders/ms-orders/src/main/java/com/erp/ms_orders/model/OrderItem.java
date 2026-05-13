package com.erp.ms_orders.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pedido_items")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "producto_sku", nullable = false)
    private String productoSku;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @Column(nullable = false)
    private Double subtotal; 
}