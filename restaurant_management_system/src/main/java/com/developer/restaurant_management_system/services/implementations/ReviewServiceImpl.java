package com.developer.restaurant_management_system.services.implementations;

import com.developer.restaurant_management_system.models.Review;
import com.developer.restaurant_management_system.models.Status;
import com.developer.restaurant_management_system.repositories.OrderRepository;
import com.developer.restaurant_management_system.repositories.ReviewRepository;

import com.developer.restaurant_management_system.services.interfaces.ReviewService;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public String addReview(Review review) {
        if (review.getRating() < 1 || review.getRating() > 5) {
            return "ERROR: Rating must be between 1 and 5 inclusive";
        }
        if (orderRepository.existsById(review.getOrderId())) {
            if (!Objects.equals(orderRepository.getReferenceById(review.getOrderId()).getUserId(), review.getUserId())) {
                return "ERROR: Unable to add a review to the order of another user";
            }
            if (orderRepository.getReferenceById(review.getOrderId()).getStatus() != Status.PAID) {
                return "ERROR: Unable to add a review to a non-paid order";
            }
            reviewRepository.save(review);
            return "Review successfully added";
        }
        return "ERROR: Unable to add a review to a non-existent order";
    }
}
