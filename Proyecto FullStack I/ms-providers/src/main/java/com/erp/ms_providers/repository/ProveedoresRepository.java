package com.erp.ms_providers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.ms_providers.model.Proveedor;
import java.util.Optional;

@Repository
public interface ProveedoresRepository extends JpaRepository<Proveedor, Long> {

    Optional<Proveedor> findByRut(String rut);
}
