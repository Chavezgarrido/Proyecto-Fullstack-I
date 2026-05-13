package com.erp.ms_sellers.repository;

import com.erp.ms_sellers.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByRut(String rut);
    List<Seller> findBySucursal(String sucursal);
    List<Seller> findByActivo();
    boolean existsByRut(String rut);
}
