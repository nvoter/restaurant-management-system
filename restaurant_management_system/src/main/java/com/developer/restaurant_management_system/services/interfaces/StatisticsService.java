package com.developer.restaurant_management_system.services.interfaces;

public interface StatisticsService {
    public String getAverageRating(String username, String password);

    public String getMostPopularDish(String username, String password);

    public String getOrdersCount(String username, String password);

    public String getRevenue(String username, String password);
}
