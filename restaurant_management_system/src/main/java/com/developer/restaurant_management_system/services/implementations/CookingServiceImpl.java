package com.developer.restaurant_management_system.services.implementations;

import com.developer.restaurant_management_system.models.Dish;
import com.developer.restaurant_management_system.models.Order;
import com.developer.restaurant_management_system.models.Status;
import com.developer.restaurant_management_system.repositories.OrderRepository;

import com.developer.restaurant_management_system.services.interfaces.CookingService;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class CookingServiceImpl implements CookingService {
    final int NUMBER_OF_CHEFS = 2;
    private final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CHEFS);
    private final LinkedBlockingQueue<Order> orders;
    private final OrderRepository orderRepository;

    public CookingServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        orders = new LinkedBlockingQueue<>();
        startCooking();
    }

    public void addOrderToQueue(Order order) {
        orders.add(order);
    }

    public void startCooking() {
        Runnable task = () -> {
            while (true) {
                try {
                    Order order = orders.take();
                    if (!order.getDishes().isEmpty()) {
                        executorService.execute(() -> processOrder(order));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        };

        executorService.execute(task);
    }

    private void processOrder(Order order) {
        order.setStatus(Status.PREPARING);
        orderRepository.save(order);
        for (Dish dish : order.getDishes()) {
            if (order.getStatus() == Status.CANCELED) {
                System.out.println("Order has been canceled");
            }
            cookDish(dish);
        }
        order.setStatus(Status.READY);
        orderRepository.save(order);
        System.out.println("The order " + order.getId() + " is ready");
    }

    private void cookDish(Dish dish) {
        executorService.submit(() -> {
            try {
                Thread.sleep(dish.getCookingTime() * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
