package com.example.SimpleOrder.service;

import com.example.SimpleOrder.exception.BadRequestException;
import com.example.SimpleOrder.exception.ResourceNotEnoughException;
import com.example.SimpleOrder.exception.ResourceNotFoundException;
import com.example.SimpleOrder.model.Inventory;
import com.example.SimpleOrder.model.Order;
import com.example.SimpleOrder.model.OrderDetail;
import com.example.SimpleOrder.payload.request.OrderRequest;
import com.example.SimpleOrder.payload.response.OrderResponse;
import com.example.SimpleOrder.repository.InventoryRepository;
import com.example.SimpleOrder.repository.OrderDetailRepository;
import com.example.SimpleOrder.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Service
public class OrderService {
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderRepository orderRepository;

    //private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Transactional(rollbackFor = Exception.class)
    public OrderResponse createOrder(OrderRequest orderRequest) {
        if (orderRequest.getInventory().size() != orderRequest.getQuantity().size()) {
            throw new BadRequestException("Lists size must match");
        }

        Order order = new Order();
        order.setDate(new Date().toString());
        order.setEmail(orderRequest.getEmail());

        orderRepository.save(order);
        orderRepository.flush();

        for (int i = 0; i < orderRequest.getInventory().size(); i++) {
            Long inventoryId = orderRequest.getInventory().get(i);
            Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(() -> new ResourceNotFoundException("InventoryId " + inventoryId + " not found"));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setInventory(inventory);

            int quantityNeeded = orderRequest.getQuantity().get(i);
            if (inventory.getQuantity() < quantityNeeded)
                throw new ResourceNotEnoughException("InventoryId " + inventoryId + " not enough");
            orderDetail.setQuantity(quantityNeeded);
            orderDetailRepository.save(orderDetail);

            inventory.setQuantity(inventory.getQuantity()-quantityNeeded);
            inventoryRepository.save(inventory);
        }

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setStatus(order.getStatus());
        return response;
    }


    @Transactional(rollbackFor = Exception.class)
    public OrderResponse updateOrder(Long orderId, OrderRequest orderRequest) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("OrderId " + orderId + " not found"));

        if (orderRequest.getInventory().size() != orderRequest.getQuantity().size()) {
            throw new BadRequestException("Lists size must match");
        }

        Collection<OrderDetail> oldDetail = orderDetailRepository.findByOrderId(orderId);
        for (OrderDetail aDetail:oldDetail) {
            Inventory inventory = inventoryRepository.findById(aDetail.getInventory().getId()).orElse(null);
            inventory.setQuantity(inventory.getQuantity() + aDetail.getQuantity());
            orderDetailRepository.delete(aDetail);
            inventoryRepository.save(inventory);
        }

        for (int i = 0; i < orderRequest.getInventory().size(); i++) {
            Long inventoryId = orderRequest.getInventory().get(i);
            Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(() -> new ResourceNotFoundException("InventoryId " + inventoryId + " not found"));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setInventory(inventory);

            int quantityNeeded = orderRequest.getQuantity().get(i);
            if (inventory.getQuantity() < quantityNeeded)
                throw new ResourceNotEnoughException("InventoryId " + inventoryId + " not enough");
            orderDetail.setQuantity(quantityNeeded);
            orderDetailRepository.save(orderDetail);

            inventory.setQuantity(inventory.getQuantity()-quantityNeeded);
            inventoryRepository.save(inventory);
        }

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setStatus(order.getStatus());
        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteOrderDetail(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("OrderId " + orderId + " not found"));

        Collection<OrderDetail> oldDetail = orderDetailRepository.findByOrderId(orderId);
        for (OrderDetail aDetail:oldDetail) {
            Inventory inventory = inventoryRepository.findById(aDetail.getInventory().getId()).orElse(null);
            inventory.setQuantity(inventory.getQuantity() + aDetail.getQuantity());
            orderDetailRepository.delete(aDetail);
            inventoryRepository.save(inventory);
        }
    }
}
