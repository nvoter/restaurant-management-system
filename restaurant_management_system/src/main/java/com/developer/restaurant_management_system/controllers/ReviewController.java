package com.developer.restaurant_management_system.controllers;

import com.developer.restaurant_management_system.models.Review;
import com.developer.restaurant_management_system.services.implementations.ReviewServiceImpl;
import com.developer.restaurant_management_system.services.interfaces.ReviewService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add-review")
    public ResponseEntity<String> addReview(@RequestBody Review review) {
        String response = reviewService.addReview(review);
        if (response.contains("successfully")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
