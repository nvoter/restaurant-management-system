package com.developer.restaurant_management_system.controllers;

import com.developer.restaurant_management_system.services.implementations.OrderServiceImpl;
import com.developer.restaurant_management_system.services.interfaces.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody List<String> dishNames, @RequestParam String username) {
        String response = orderService.createOrder(dishNames, username);
        if (response.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/{orderId}/addDish")
    public ResponseEntity<String> addDishToOrder(@PathVariable Integer orderId, @RequestParam Integer dishId, @RequestParam Integer userId) {
        String response = orderService.addDishToOrder(orderId, dishId, userId);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Integer orderId, @RequestParam Integer userId) {
        String response = orderService.cancelOrder(orderId, userId);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<String> removeOrder(@PathVariable Integer orderId) {
        String response = orderService.removeOrder(orderId);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{orderId}/get-status")
    public ResponseEntity<String> getOrderStatus(@PathVariable Integer orderId) {
        String response = orderService.getOrderStatus(orderId);
        if (response.contains("ERROR")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/pay")
    public ResponseEntity<String> payForOrder(@PathVariable Integer orderId, @RequestParam Integer userId) {
        String response = orderService.payForOrder(orderId, userId);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
