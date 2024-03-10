package com.developer.restaurant_management_system.services.interfaces;

import com.developer.restaurant_management_system.models.Dish;

import java.util.List;

public interface DishService {
    String addDish(String username, String password, Dish dish);

    String removeDish(String username, String password, Integer dishId);

    String updateDish(String username, String password, Integer dishId, String description, Double price, Integer quantity, Integer cookingTime);

    List<Dish> getAll();

    Dish getById(Integer dishId);
}
