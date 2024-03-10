package com.developer.restaurant_management_system.controllers;

import com.developer.restaurant_management_system.services.implementations.StatisticsServiceImpl;
import com.developer.restaurant_management_system.services.interfaces.StatisticsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsServiceImpl statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/average-rating")
    public ResponseEntity<String> getAverageRating(@RequestParam String username, @RequestParam String password) {
        String response = statisticsService.getAverageRating(username, password);
        if (response.contains("ERROR")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/most-popular-dish")
    public ResponseEntity<String> getMostPopularDish(@RequestParam String username, @RequestParam String password) {
        String response = statisticsService.getMostPopularDish(username, password);
        if (response.contains("ERROR")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/orders-count")
    public ResponseEntity<String> getOrdersCount(@RequestParam String username, @RequestParam String password) {
        String response = statisticsService.getOrdersCount(username, password);
        if (response.contains("ERROR")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/revenue")
    public ResponseEntity<String> getRevenue(@RequestParam String username, @RequestParam String password) {
        String response = statisticsService.getRevenue(username, password);
        if (response.contains("ERROR")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
