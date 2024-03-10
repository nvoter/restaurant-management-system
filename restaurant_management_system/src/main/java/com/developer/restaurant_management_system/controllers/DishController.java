package com.developer.restaurant_management_system.controllers;

import com.developer.restaurant_management_system.models.Dish;
import com.developer.restaurant_management_system.services.implementations.DishServiceImpl;
import com.developer.restaurant_management_system.services.interfaces.DishService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {
    private final DishService dishService;

    public DishController(DishServiceImpl dishService) {
        this.dishService = dishService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addDish(@RequestParam String username, @RequestParam String password, @RequestBody Dish dish) {
        String response = dishService.addDish(username, password, dish);
        if (response.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{dishId}/delete")
    public ResponseEntity<String> removeDish(@RequestParam String username, @RequestParam String password, @PathVariable Integer dishId) {
        String response = dishService.removeDish(username, password, dishId);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{dishId}/update")
    public ResponseEntity<String> updateDish(@RequestParam String username, @RequestParam String password, @PathVariable Integer dishId, @RequestBody Dish dish) {
        String response = dishService.updateDish(username, password, dishId, dish.getDescription(), dish.getPrice(), dish.getQuantity(), dish.getCookingTime());
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/menu")
    public ResponseEntity<List<Dish>> getAllDishes() {
        List<Dish> dishes = dishService.getAll();
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/{dishId}")
    public ResponseEntity<Dish> getDishById(@PathVariable Integer dishId) {
        Dish dish = dishService.getById(dishId);
        if (dish != null) {
            return ResponseEntity.ok(dish);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}