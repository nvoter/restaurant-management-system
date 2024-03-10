package com.developer.restaurant_management_system.services.implementations;

import com.developer.restaurant_management_system.models.*;
import com.developer.restaurant_management_system.repositories.OrderRepository;
import com.developer.restaurant_management_system.repositories.ReviewRepository;

import com.developer.restaurant_management_system.repositories.UserRepository;
import com.developer.restaurant_management_system.services.interfaces.StatisticsService;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private String login(String username, String password) {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            return "ERROR: Username does not exist";
        } else if (!Objects.equals(user.getPassword(), password)) {
            return "ERROR: Wrong password";
        } else if (user.getRole() != Role.ADMIN) {
            return "ERROR: User must be admin";
        }
        return "Succeeded";
    }

    @Transactional
    public String getAverageRating(String username, String password) {
        String loginResponse = login(username, password);
        if (!loginResponse.contains("ERROR")) {
            double averageRating = 0.0;
            for (Review review : reviewRepository.findAll()) {
                averageRating += review.getRating();
            }
            if (reviewRepository.count() != 0) {
                averageRating /= reviewRepository.count();
                return Double.toString(averageRating);
            }
            return Double.toString(0.0);
        } else {
            return loginResponse;
        }
    }

    @Transactional
    public String getMostPopularDish(String username, String password) {
        String loginResponse = login(username, password);
        if (!loginResponse.contains("ERROR")) {
            HashMap<String, Integer> popularity = new HashMap<>();
            int maxValue = -1;
            String maxKey = "";
            for (Order order : orderRepository.findAll()) {
                for (Dish dish : order.getDishes()) {
                    if (popularity.containsKey(dish.getName())) {
                        popularity.replace(dish.getName(), popularity.get(dish.getName()) + 1);
                    } else {
                        popularity.put(dish.getName(), 1);
                    }
                    if (popularity.get(dish.getName()) > maxValue) {
                        maxValue = popularity.get(dish.getName());
                        maxKey = dish.getName();
                    }
                }
            }
            if (!Objects.equals(maxKey, "")) {
                return maxKey;
            }
            return "ERROR: Database does not contain orders";
        } else {
            return loginResponse;
        }
    }

    @Transactional
    public String getOrdersCount(String username, String password) {
        String loginResponse = login(username, password);
        if (!loginResponse.contains("ERROR")) {
            return Long.toString(orderRepository.count());
        } else {
            return loginResponse;
        }
    }

    @Transactional
    public String getRevenue(String username, String password) {
        String loginResponse = login(username, password);
        if (!loginResponse.contains("ERROR")) {
            List<Order> paidOrders = orderRepository.findByStatus(Status.PAID);
            Double revenue = 0.0;
            for (Order order : paidOrders) {
                revenue += order.getCost();
            }
            return revenue.toString();
        } else {
            return loginResponse;
        }
    }
}
