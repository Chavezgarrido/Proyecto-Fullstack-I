package com.erp.ms_orders.controller;

import com.erp.ms_orders.dto.OrderRequestDTO;
import com.erp.ms_orders.model.Order;
import com.erp.ms_orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAll(){
        return ResponseEntity.ok(orderService.getAll());
    }
    
    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody OrderRequestDTO request){
        Order newOrder = orderService.create(request);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }


}
