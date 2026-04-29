package com.erp.ms_report.service;

import com.erp.ms_report.client.InventarioClient;
import com.erp.ms_report.client.VentasClient;
import com.erp.ms_report.dto.ProductoDTO;
import com.erp.ms_report.dto.ReporteResumenDTO;
import com.erp.ms_report.dto.VentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    @Autowired
    private VentasClient ventasClient;

    @Autowired
    private InventarioClient inventarioClient;

    public ReporteResumenDTO obtenerResumen(){
        List<VentaDTO> ventas = ventasClient.obtenerTodasLasVentas();
        List<ProductoDTO> productos = inventarioClient.obtenerTodosLosProductos();

        Double ingresos = ventas.stream().mapToDouble(VentaDTO::getPrecioTotal).sum();

        Long totalVentas = (long) ventas.size();

        String productoEstrella = ventas.stream().collect(Collectors.groupingBy(VentaDTO::getProductoId, Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByKey())
        .map(entry -> "ID Producto: " + entry.getKey() + " (Vendido " + entry.getValue() + " veces)").orElse("Sin ventas");

        List<String> alertas = productos.stream().filter(p -> p.getStock() < 5).map(p -> p.getNombre() + " - Stock actual: " + p.getStock()).collect(Collectors.toList());

        return new ReporteResumenDTO(ingresos, totalVentas, productoEstrella, alertas);
    }
}
