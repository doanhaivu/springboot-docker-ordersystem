package com.example.SimpleOrder.controller;

import com.example.SimpleOrder.exception.ResourceNotFoundException;
import com.example.SimpleOrder.model.Order;
import com.example.SimpleOrder.payload.request.OrderRequest;
import com.example.SimpleOrder.payload.response.OrderResponse;
import com.example.SimpleOrder.repository.OrderRepository;
import com.example.SimpleOrder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @PostMapping("/orders")
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @PutMapping("/orders/{orderId}")
    public OrderResponse updateOrder(@PathVariable Long orderId, @Valid @RequestBody OrderRequest orderRequest) {
        return orderService.updateOrder(orderId, orderRequest);
    }


    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        return orderRepository.findById(orderId).map(order -> {
            orderService.deleteOrderDetail(orderId);
            orderRepository.delete(order);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("OrderId " + orderId + " not found"));
    }

}