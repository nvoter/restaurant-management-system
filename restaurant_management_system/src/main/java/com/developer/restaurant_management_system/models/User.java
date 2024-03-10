package com.developer.restaurant_management_system.models;

import lombok.*;

import jakarta.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(unique = true)
    @Setter
    private String username;
    @Setter
    @Column
    private String password;
    @Setter
    @Column
    private Role role = Role.GUEST;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
