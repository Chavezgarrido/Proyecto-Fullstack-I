package com.erp.ms_orders.service;

import com.erp.ms_orders.client.ClientFeignClient;
import com.erp.ms_orders.client.InventoryFeignClient;
import com.erp.ms_orders.client.SellerFeignClient;
import com.erp.ms_orders.dto.ItemRequestDTO;
import com.erp.ms_orders.dto.OrderRequestDTO;
import com.erp.ms_orders.dto.ClientDTO;
import com.erp.ms_orders.dto.SellerDTO;
import com.erp.ms_orders.dto.ProductDTO;
import com.erp.ms_orders.model.Order;
import com.erp.ms_orders.model.OrderItem;
import com.erp.ms_orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientFeignClient clienteClient;
    private final SellerFeignClient sellerClient;
    private final InventoryFeignClient inventoryClient;

    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order create(OrderRequestDTO request) {
        log.info("Iniciando creación de pedido para cliente " + request.getClienteRut());
        
        ClientDTO client = clienteClient.getByRut(request.getClienteRut());
        if (client == null) {
            throw new RuntimeException("El cliente no existe en el sistema");
        }

        SellerDTO seller = sellerClient.getByRut(request.getVendedorRut());
        if (seller == null) {
            throw new RuntimeException("El vendedor no existe en el sistema");
        }

        Order newOrder = new Order();
        newOrder.setClienteRut(client.getRut());
        newOrder.setVendedorRut(seller.getRut());

        List<OrderItem> items = new ArrayList<>();
        double totalPedido = 0.0;

        for (ItemRequestDTO itemDTO : request.getItems()) {
            ProductDTO product = inventoryClient.getBySku(itemDTO.getProductoSku());

            if (product == null) {
                throw new RuntimeException("Producto sku " + itemDTO.getProductoSku() + " no encontrado");
            }

            if (product.getStock() < itemDTO.getCantidad()) {
                throw new RuntimeException(
                        "Stock insuficiente para " + product.getNombre() + ". Disponible: " + product.getStock());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProductoSku(product.getSku());
            orderItem.setCantidad(itemDTO.getCantidad());
            orderItem.setPrecioUnitario((double) product.getPrecio());

            double subtotal = (double) product.getPrecio() * itemDTO.getCantidad();
            orderItem.setSubtotal(subtotal);

            totalPedido += subtotal;
            items.add(orderItem);

            inventoryClient.updateStock(product.getSku(), itemDTO.getCantidad());
        }

        newOrder.setItems(items);
        newOrder.setTotal(totalPedido);

        log.info("Pedido procesado con éxito. Total: " + totalPedido);
        return orderRepository.save(newOrder);
    }
}