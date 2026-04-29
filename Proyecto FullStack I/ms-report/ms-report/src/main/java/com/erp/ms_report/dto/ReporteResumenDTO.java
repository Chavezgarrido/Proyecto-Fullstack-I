package com.erp.ms_report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ReporteResumenDTO {
    private Double ingresosTotales;
    private Long totalVentasRealizadas;
    private String productoMasVendido;
    private List<String> alertasStockBajo;
}
