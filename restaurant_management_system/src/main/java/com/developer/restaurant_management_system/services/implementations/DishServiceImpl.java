package com.developer.restaurant_management_system.services.implementations;

import com.developer.restaurant_management_system.models.Dish;
import com.developer.restaurant_management_system.models.Role;
import com.developer.restaurant_management_system.models.User;
import com.developer.restaurant_management_system.repositories.DishRepository;

import com.developer.restaurant_management_system.repositories.UserRepository;
import com.developer.restaurant_management_system.services.interfaces.DishService;
import lombok.AllArgsConstructor;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final UserRepository userRepository;

    private String login(String username, String password) {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            return "ERROR: Username does not exist";
        } else if (!Objects.equals(user.getPassword(), password)) {
            return "ERROR: Wrong password";
        } else if (user.getRole() != Role.ADMIN) {
            return "ERROR: User must be admin";
        }
        return "Succeeded";
    }

    @Transactional
    public String addDish(String username, String password, Dish dish) {
        String loginResponse = login(username, password);
        if (!loginResponse.contains("ERROR")) {
            if (dishRepository.getByName(dish.getName()).isPresent()) {
                return "ERROR: Dish already exists in the menu";
            }
            dishRepository.save(dish);
            return "New dish successfully added";
        } else {
            return loginResponse;
        }
    }

    @Transactional
    public String removeDish(String username, String password, Integer dishId) {
        String loginResponse = login(username, password);
        if (!loginResponse.contains("ERROR")) {
            Optional<Dish> dishOptional = dishRepository.findById(dishId);
            if (dishOptional.isEmpty()) {
                return "ERROR: Dish to delete not found";
            }
            dishRepository.deleteById(dishId);
            return "Dish item successfully deleted";
        } else {
            return loginResponse;
        }
    }

    @Transactional
    public String updateDish(String username, String password, Integer dishId, String description, Double price, Integer quantity, Integer cookingTime) {
        String loginResponse = login(username, password);
        if (!loginResponse.contains("ERROR")) {
            Optional<Dish> dishOptional = dishRepository.findById(dishId);
            if (dishOptional.isEmpty()) {
                return "ERROR: Dish to update not found";
            }
            Dish dish = dishOptional.get();
            if (description != null) {
                dish.setDescription(description);
            }
            if (price != null) {
                dish.setPrice(price);
            }
            if (quantity != null) {
                dish.setQuantity(quantity);
            }
            if (cookingTime != null) {
                dish.setCookingTime(cookingTime);
            }
            dishRepository.save(dish);
            return "Dish successfully updated";
        } else {
            return loginResponse;
        }
    }

    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    public Dish getById(Integer dishId) {
        return dishRepository.findById(dishId).orElse(null);
    }
}