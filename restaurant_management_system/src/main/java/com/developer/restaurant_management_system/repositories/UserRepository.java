package com.developer.restaurant_management_system.repositories;

import com.developer.restaurant_management_system.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User getByUsername(String username);
}
