package com.likelion14.spring_session_10.controller;

import com.likelion14.spring_session_10.domain.Inventory;
import com.likelion14.spring_session_10.repository.InventoryRepository;
import com.likelion14.spring_session_10.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@Valid @RequestBody Inventory inventory) {
        Inventory saved = inventoryService.createInventory(inventory);
        return ResponseEntity.status(201).body(saved);
    }
}
