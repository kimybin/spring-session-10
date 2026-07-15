package com.likelion14.spring_session_10.service;

import com.likelion14.spring_session_10.domain.Inventory;
import com.likelion14.spring_session_10.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Inventory createInventory(Inventory inventory) {
        if(inventoryRepository.existsByNameAndFoodType(inventory.getName(), inventory.getFoodType())) {
            throw new IllegalArgumentException("이미 존재하는 제품입니다.");
        }
        return inventoryRepository.save(inventory);
    }

}
