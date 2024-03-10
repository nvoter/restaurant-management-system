package com.developer.restaurant_management_system.repositories;

import com.developer.restaurant_management_system.models.Dish;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    Optional<Dish> getByName(String name);
}
