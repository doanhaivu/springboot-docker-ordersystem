package com.example.SimpleOrder.repository;

import com.example.SimpleOrder.model.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    Page<OrderDetail> findByInventoryId(Long inventoryId, Pageable pageable);
    Optional<OrderDetail> findByIdAndInventoryId(Long id, Long inventoryId);

    Collection<OrderDetail> findByOrderId(Long orderId);

    Page<OrderDetail> findByOrderId(Long orderId, Pageable pageable);
    Optional<OrderDetail> findByIdAndOrderId(Long id, Long orderId);
}