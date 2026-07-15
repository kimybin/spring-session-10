package com.likelion14.spring_session_10.repository;

import com.likelion14.spring_session_10.domain.FoodType;
import com.likelion14.spring_session_10.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsByNameAndFoodType(String name, FoodType foodType);
}
