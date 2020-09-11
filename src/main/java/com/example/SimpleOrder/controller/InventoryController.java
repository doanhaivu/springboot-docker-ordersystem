package com.example.SimpleOrder.controller;


import com.example.SimpleOrder.exception.ResourceNotFoundException;
import com.example.SimpleOrder.model.Inventory;
import com.example.SimpleOrder.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping("/inventory")
    public Page<Inventory> getAllInventory(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    @PostMapping("/inventory")
    public Inventory createInventory(@Valid @RequestBody Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @PutMapping("/inventory/{inventoryId}")
    public Inventory updateInventory(@PathVariable Long inventoryId, @Valid @RequestBody Inventory inventoryRequest) {
        return inventoryRepository.findById(inventoryId).map(inventory -> {
            inventory.setName(inventoryRequest.getName());
            inventory.setDescription(inventoryRequest.getDescription());
            inventory.setPrice(inventoryRequest.getPrice());
            inventory.setQuantity(inventoryRequest.getQuantity());
            return inventoryRepository.save(inventory);
        }).orElseThrow(() -> new ResourceNotFoundException("InventoryId " + inventoryId + " not found"));
    }

    @DeleteMapping("/inventory/{inventoryId}")
    public ResponseEntity<?> deleteInventory(@PathVariable Long inventoryId) {
        return inventoryRepository.findById(inventoryId).map(inventory -> {
            inventoryRepository.delete(inventory);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("InventoryId " + inventoryId + " not found"));
    }

}