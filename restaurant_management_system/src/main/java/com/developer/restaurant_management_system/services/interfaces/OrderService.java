package com.developer.restaurant_management_system.services.interfaces;

import java.util.List;

public interface OrderService {
    String createOrder(List<String> dishNames, String username);

    String addDishToOrder(Integer orderId, Integer dishId, Integer userId);

    String cancelOrder(Integer orderId, Integer userId);

    String removeOrder(Integer id);

    String getOrderStatus(Integer orderId);

    String payForOrder(Integer orderId, Integer userId);
}
