package com.erp.ms_clients.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.erp.ms_clients.model.Client; 
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByRut(String rut);

    Optional<Client> findByEmail(String email);
}
