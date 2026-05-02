package main.java.com.erp.ms_sellers.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.erp.ms_sellers.model.Vendedor;
import java.util.Optional;


@Repository

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    
    
    Optional<Vendedor> findByRut(String rut);
}