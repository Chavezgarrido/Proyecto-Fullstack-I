package com.erp.ms_sales.service;

import com.erp.ms_sales.client.InventoryClient;
import com.erp.ms_sales.client.CustomerClient;
import com.erp.ms_sales.dto.ProductoDTO;
import com.erp.ms_sales.model.Venta;
import com.erp.ms_sales.repository.VentaRepository;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaService {
   
    private final VentaRepository ventaRepository;
    private final InventoryClient inventoryClient;
    private final CustomerClient customerClient;

    @Transactional(readOnly = true)
    public List<Venta> obtenerTodas(){
        return ventaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Venta obtenerPorId(Long id){
        return ventaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Venta con id " + id + " no encontrada."));
    }

    @Transactional
    public Venta procesarVenta(Long productoId, String clienteRut, Integer cantidad){
        if(productoId == null) throw new RuntimeException("El id del producto es obligatorio");
        if(clienteRut == null || clienteRut.isBlank()) throw new RuntimeException("El rut del cliente es obligatorio");
        if(cantidad == null || cantidad <= 0) throw new RuntimeException("La cantidad debe ser mayor a cero.");
        
        customerClient.buscarPorRut(clienteRut);

        ProductoDTO producto = inventoryClient.getProductoById(productoId);

        if(producto.getStock() < cantidad){
            throw new RuntimeException("Stock insuficiente para " + producto.getNombre() + ". Disponible: " + producto.getStock());
        }

        inventoryClient.restarStock(productoId, cantidad);

        Venta venta = new Venta();
        venta.setProductoId(productoId);
        venta.setClienteRut(clienteRut);
        venta.setCantidad(cantidad);
        venta.setPrecioTotal(producto.getPrecio() * cantidad);

        return ventaRepository.save(venta);
    } 

    @Transactional
    public void eliminarVenta(Long id){
        if(!ventaRepository.existsById(id)){
            throw new RuntimeException("No se puede eliminar. Venta con id " + id + " no existe.");
        }
        ventaRepository.deleteById(id);
    }

}
