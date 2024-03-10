package com.developer.restaurant_management_system.services.implementations;

import com.developer.restaurant_management_system.models.*;
import com.developer.restaurant_management_system.repositories.DishRepository;
import com.developer.restaurant_management_system.repositories.OrderRepository;

import com.developer.restaurant_management_system.repositories.UserRepository;
import com.developer.restaurant_management_system.services.interfaces.CookingService;
import com.developer.restaurant_management_system.services.interfaces.OrderService;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;
    private final CookingService cookingService;

    @Transactional
    public String createOrder(List<String> dishNames, String username) {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            return "ERROR: User does not exist";
        }
        if (user.getRole() != Role.GUEST) {
            return "ERROR: Only guests can create orders";
        }
        List<Dish> dishes = new ArrayList<>();
        for (String name : dishNames) {
            Optional<Dish> dishOptional = dishRepository.getByName(name);
            if (dishOptional.isPresent()) {
                Dish dish = dishOptional.get();
                if (dish.getQuantity() > 0) {
                    dish.setQuantity(dish.getQuantity() - 1);
                    dishes.add(dish);
                } else {
                    return "ERROR: Not enough " + name + " in stock";
                }
            } else {
                return "ERROR: Dish " + name + " is not available in the menu";
            }
        }
        if (dishes.isEmpty()) {
            return "ERROR: No valid dishes provided";
        }
        Order order = new Order();
        order.setUserId(user.getId());
        order.setDishes(dishes);
        order.setStatus(Status.ACCEPTED);
        dishRepository.saveAll(dishes);
        orderRepository.save(order);
        cookingService.addOrderToQueue(order);
        return "Order successfully created";
    }

    @Transactional
    public String addDishToOrder(Integer orderId, Integer dishId, Integer userId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return "ERROR: Order does not exist";
        }
        Order order = orderOptional.get();
        if (!order.getUserId().equals(userId)) {
            return "ERROR: Order belongs to another user";
        }
        if (order.getStatus() != Status.ACCEPTED && order.getStatus() != Status.PREPARING) {
            return "ERROR: It is impossible to add a dish to an order that is not in process";
        }
        if (!dishRepository.existsById(dishId)) {
            return "ERROR: Dish is not available in the menu";
        }
        Dish dish = dishRepository.getReferenceById(dishId);
        order.getDishes().add(dish);
        orderRepository.save(order);
        return "Dish successfully added to order";
    }

    @Transactional
    public String cancelOrder(Integer orderId, Integer userId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return "ERROR: Order does not exist";
        }
        Order order = orderOptional.get();
        if (!order.getUserId().equals(userId)) {
            return "ERROR: Order belongs to another user";
        }
        if (order.getStatus() != Status.ACCEPTED && order.getStatus() != Status.PREPARING) {
            return "ERROR: It is impossible to cancel order that is not in process";
        }
        order.setStatus(Status.CANCELED);
        orderRepository.save(order);
        return "Order successfully canceled";
    }

    @Transactional
    public String removeOrder(Integer id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            return "ERROR: Order does not exist";
        }
        orderRepository.deleteById(id);
        return "Order successfully deleted";
    }

    @Transactional
    public String getOrderStatus(Integer orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return "ERROR: Order does not exist";
        }
        Order order = orderOptional.get();
        return order.getStatus().name();
    }

    @Transactional
    public String payForOrder(Integer orderId, Integer userId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return "ERROR: Order does not exist";
        }
        Order order = orderOptional.get();
        if (!order.getUserId().equals(userId)) {
            return "ERROR: Order belongs to another user";
        }
        switch (order.getStatus()) {
            case CANCELED:
                return "ERROR: Unable to pay for a canceled order";
            case PAID:
                return "ERROR: Unable to pay for an already paid order";
            case READY:
                order.setStatus(Status.PAID);
                orderRepository.save(order);
                return "Order successfully paid for";
            default:
                return "ERROR: Unable to pay for a non-ready order";
        }
    }
}