package com.erp.ms_orders.repository;

import com.erp.ms_orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByClienteRut(String clienteRut);

    List<Order> findBySellerRut(String vendedorRut);
}
