package com.developer.restaurant_management_system.repositories;

import com.developer.restaurant_management_system.models.Review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
