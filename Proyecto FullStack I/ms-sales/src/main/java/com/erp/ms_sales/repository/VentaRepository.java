package com.erp.ms_sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.ms_sales.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long>{
}
