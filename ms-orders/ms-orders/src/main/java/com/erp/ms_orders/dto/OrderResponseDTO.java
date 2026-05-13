package com.erp.ms_orders.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private String clienteRut;
    private String vendedorRut;
    private List<OrderItemDTO> items;
    private Double total;
    private LocalDateTime fechaPedido;
}

@Data
class OrderItemDTO {
    private String productoSku;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}