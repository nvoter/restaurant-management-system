package com.developer.restaurant_management_system.models;

import lombok.*;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Setter
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column
    private Integer orderId;
    @Column
    private Integer userId;
    @Column
    private Integer rating;
    @Column
    private String comment;
}
