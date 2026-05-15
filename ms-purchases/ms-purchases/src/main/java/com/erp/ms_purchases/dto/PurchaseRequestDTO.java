package com.erp.ms_purchases.dto;

import lombok.Data;

@Data
public class PurchaseRequestDTO {
    private String productSku;
    private String providerRut;
    private Integer cantidad;
    private int precioUnitario;
}
