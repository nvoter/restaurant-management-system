package com.developer.restaurant_management_system.repositories;

import com.developer.restaurant_management_system.models.Order;
import com.developer.restaurant_management_system.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> getOrdersByUserId(Integer userId);

    List<Order> findByStatus(Status status);
}
