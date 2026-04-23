package com.erp.ms_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.ms_inventory.model.Producto;

import java.util.List;
import java.util.Optional;

@Repository 
public interface ProductoRepository extends JpaRepository<Producto, Long>{

    boolean existsByNombre(String nombre);

    Optional<Producto> findByNombre(String nombre);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByStockLessThan(Integer limite);
}
