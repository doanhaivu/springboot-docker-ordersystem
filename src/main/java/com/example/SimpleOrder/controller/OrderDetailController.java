package com.example.SimpleOrder.controller;

import com.example.SimpleOrder.model.OrderDetail;
import com.example.SimpleOrder.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderDetailController {

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @GetMapping("/orders/{orderId}/orderdetail")
    public Page<OrderDetail> getAllOrderDetailByOrderId(@PathVariable (value = "orderId") Long orderId,
                                                        Pageable pageable) {
        return orderDetailRepository.findByOrderId(orderId, pageable);
    }

    @GetMapping("/inventory/{inventoryId}/orderdetail")
    public Page<OrderDetail> getAllOrderDetailByInventoryId(@PathVariable (value = "inventoryId") Long inventoryId,
                                                        Pageable pageable) {
        return orderDetailRepository.findByInventoryId(inventoryId, pageable);
    }

}