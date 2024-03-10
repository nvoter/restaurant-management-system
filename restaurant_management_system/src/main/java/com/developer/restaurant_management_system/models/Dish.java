package com.developer.restaurant_management_system.models;

import lombok.*;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Setter
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(unique = true)
    private String name;
    @Column
    private String description;
    @Column
    private double price;
    @Column
    private int quantity;
    @Column
    private int cookingTime;
}
