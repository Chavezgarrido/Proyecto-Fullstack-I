package com.erp.ms_inventory.service;

import com.erp.ms_inventory.model.Producto;
import com.erp.ms_inventory.repository.ProductoRepository;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Producto> obtenerTodos(){
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto obtenerPorId(Long id){
        return productoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto id " + id + " no encontrado."));
    }

    @Transactional(readOnly = true)
    public List<Producto> buscarPorNombre(String nombre){
        List<Producto> productos = productoRepository.findByNombreContainingIgnoreCase(nombre);

        if(productos.isEmpty()){
            throw new RuntimeException("No se encontraron productos que coincidan con: " + nombre);
        }
        return productos;
    }

    @Transactional(readOnly = true)
    public List<Producto> obtenerStockBajo(Integer Limite){
        return productoRepository.findByStockLessThan(Limite);
    }

    @Transactional
    public Producto guardar(Producto producto){
        if (productoRepository.existsByNombre(producto.getNombre())){
            throw new RuntimeException("Ya existe un producto con el nombre : " + producto.getNombre());
        }
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizar(Long id, Producto detalles){
        Producto existente = obtenerPorId(id);
        //Solo actualizamos nombre y precio, los unicos permitidos
        existente.setNombre(detalles.getNombre());
        existente.setPrecio(detalles.getPrecio());
        return productoRepository.save(existente);
    }

    @Transactional
    public void eliminar(Long id){
        if(!productoRepository.existsById(id)){
            throw new RuntimeException("No se puede eliminar. Producto con id " + id + " no existe");
        }
        productoRepository.deleteById(id);
    }

    @Transactional
    //Metodo para ingreso de mercancía
    public Producto aumentarStock(Long id, Integer cantidad){
        if (cantidad <= 0) throw new RuntimeException("La cantidad a sumar debe ser mayor a cero.");

        Producto producto = obtenerPorId(id);
        producto.setStock(producto.getStock() + cantidad);
        return productoRepository.save(producto);
    }

    @Transactional
    //Metodo para salida de mercancía
    public Producto restarStock(Long id, Integer cantidad){
        Producto producto = obtenerPorId(id);
        if(producto.getStock() < cantidad){
            throw new RuntimeException("Stock insuficiente para " + producto.getNombre() + ". Disponible: " + producto.getStock());
        }
        producto.setStock(producto.getStock() - cantidad);
        return productoRepository.save(producto);
    }

}
