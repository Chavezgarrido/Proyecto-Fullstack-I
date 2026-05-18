package com.erp.ms_orders.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDTO {

    @NotBlank(message = "El RUT del cliente es obligatorio")
    private String clienteRut;

    @NotBlank(message = "El RUT del vendedor es obligatorio")
    private String vendedorRut;

    @NotEmpty(message = "El pedido debe tener al menos un item")
    @Valid
    private List<ItemRequestDTO> items;
}