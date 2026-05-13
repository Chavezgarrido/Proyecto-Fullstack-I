package com.erp.ms_providers.repository;

import com.erp.ms_providers.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    Optional<Provider> findByRut(String rut);

    List<Provider> findByCategoria(String categoria);

    List<Provider> findByRazonSocial(String razonSocial);

    boolean existsByRut(String rut);
}
