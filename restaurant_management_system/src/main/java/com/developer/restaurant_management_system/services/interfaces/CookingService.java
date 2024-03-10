package com.developer.restaurant_management_system.services.interfaces;

import com.developer.restaurant_management_system.models.Order;

public interface CookingService {
    void addOrderToQueue(Order order);

    void startCooking();
}
