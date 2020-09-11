package com.example.SimpleOrder.controller;

import com.example.SimpleOrder.exception.ResourceNotFoundException;
import com.example.SimpleOrder.model.OrderDetail;
import com.example.SimpleOrder.repository.InventoryRepository;
import com.example.SimpleOrder.repository.OrderDetailRepository;
import com.example.SimpleOrder.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class OrderDetailController {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping("/orders/{orderId}/orderdetail")
    public Page<OrderDetail> getAllOrderDetailByOrderId(@PathVariable (value = "orderId") Long orderId,
                                                        Pageable pageable) {
        return orderDetailRepository.findByOrderId(orderId, pageable);
    }

    @PostMapping("/orders/{orderId}/orderdetail")
    public OrderDetail createOrderDetailFromOrder(@PathVariable (value = "orderId") Long orderId,
                                 @Valid @RequestBody OrderDetail orderDetail) {
        return orderRepository.findById(orderId).map(order -> {
            orderDetail.setOrder(order);
            return orderDetailRepository.save(orderDetail);
        }).orElseThrow(() -> new ResourceNotFoundException("OrderId " + orderId + " not found"));
    }

    @PutMapping("/orders/{orderId}/orderdetail/{orderDetailId}")
    public OrderDetail updateOrderDetailFromOrder(@PathVariable (value = "orderId") Long orderId,
                                 @PathVariable (value = "orderDetailId") Long orderDetailId,
                                 @Valid @RequestBody OrderDetail orderDetailRequest) {
        if(!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("OrderId " + orderId + " not found");
        }

        return orderDetailRepository.findById(orderDetailId).map(orderDetail -> {
            orderDetail.setQuantity(orderDetailRequest.getQuantity());
            return orderDetailRepository.save(orderDetail);
        }).orElseThrow(() -> new ResourceNotFoundException("OrderDetailId " + orderDetailId + "not found"));
    }

    @DeleteMapping("/orders/{orderId}/orderdetail/{orderDetailId}")
    public ResponseEntity<?> deleteOrderDetailFromOrder(@PathVariable (value = "orderId") Long orderId,
                                           @PathVariable (value = "orderDetailId") Long orderDetailId) {
        return orderDetailRepository.findByIdAndOrderId(orderDetailId, orderId).map(orderDetail -> {
            orderDetailRepository.delete(orderDetail);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Order detail not found with id " + orderDetailId + " and orderId " + orderId));
    }

    @GetMapping("/inventory/{inventoryId}/orderdetail")
    public Page<OrderDetail> getAllOrderDetailByInventoryId(@PathVariable (value = "inventoryId") Long inventoryId,
                                                        Pageable pageable) {
        return orderDetailRepository.findByInventoryId(inventoryId, pageable);
    }

    @PostMapping("/inventory/{inventoryId}/orderdetail")
    public OrderDetail createOrderDetailFromInventory(@PathVariable (value = "inventoryId") Long inventoryId,
                                         @Valid @RequestBody OrderDetail orderDetail) {
        return inventoryRepository.findById(inventoryId).map(inventory -> {
            orderDetail.setInventory(inventory);
            return orderDetailRepository.save(orderDetail);
        }).orElseThrow(() -> new ResourceNotFoundException("InventoryId " + inventoryId + " not found"));
    }

    @PutMapping("/inventory/{inventoryId}/orderdetail/{orderDetailId}")
    public OrderDetail updateOrderDetailFromInventory(@PathVariable (value = "inventoryId") Long inventoryId,
                                         @PathVariable (value = "orderDetailId") Long orderDetailId,
                                         @Valid @RequestBody OrderDetail orderDetailRequest) {
        if(!inventoryRepository.existsById(inventoryId)) {
            throw new ResourceNotFoundException("InventoryId " + inventoryId + " not found");
        }

        return orderDetailRepository.findById(orderDetailId).map(orderDetail -> {
            orderDetail.setQuantity(orderDetailRequest.getQuantity());
            return orderDetailRepository.save(orderDetail);
        }).orElseThrow(() -> new ResourceNotFoundException("OrderDetailId " + orderDetailId + "not found"));
    }

    @DeleteMapping("/inventory/{inventoryId}/orderdetail/{orderDetailId}")
    public ResponseEntity<?> deleteOrderDetailFromInventory(@PathVariable (value = "inventoryId") Long inventoryId,
                                               @PathVariable (value = "orderDetailId") Long orderDetailId) {
        return orderDetailRepository.findByIdAndInventoryId(orderDetailId, inventoryId).map(orderDetail -> {
            orderDetailRepository.delete(orderDetail);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Order detail not found with id " + orderDetailId + " and inventoryId " + inventoryId));
    }
}