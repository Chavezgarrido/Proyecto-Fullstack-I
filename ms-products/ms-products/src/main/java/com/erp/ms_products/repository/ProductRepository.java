package com.erp.ms_products.repository;

import com.erp.ms_products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String>{

    Optional<Product> findBySku(String sku);

    Optional<Product> findById(String id);

    List<Product> findByNombre(String nombre);

    List<Product> findByStockLessThan(Integer limite);

    boolean existsBySku(String sku);
}
