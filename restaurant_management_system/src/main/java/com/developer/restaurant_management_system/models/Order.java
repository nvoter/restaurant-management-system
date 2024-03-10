package com.developer.restaurant_management_system.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column
    private Integer userId;
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dish> dishes;
    private int cookingTime;
    @Column
    private Status status;

    public synchronized void increaseCookingTime(int addedPeriod) {
        cookingTime += addedPeriod;
    }

    @JsonCreator
    public Order(Dish[] dishes) {
        this.dishes = Arrays.stream(dishes).toList();
        for (Dish dish : dishes) {
            increaseCookingTime(dish.getCookingTime());
        }
        status = Status.ACCEPTED;
    }

    public Double getCost() {
        double cost = 0.0;
        for (Dish dish : dishes) {
            cost += dish.getPrice();
        }
        return cost;
    }
}