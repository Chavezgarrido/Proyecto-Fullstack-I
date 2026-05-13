package com.erp.ms_products.repository;

import com.erp.ms_products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    Optional<Product> findBySku(String sku);

    List<Product> findByName(String nombre);

    List<Product> finbByStockLessThan(Integer limite);

    boolean existsBySku(String sku);
}
