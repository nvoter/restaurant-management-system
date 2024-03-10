package com.developer.restaurant_management_system.services.interfaces;

import com.developer.restaurant_management_system.models.User;

public interface UserService {
    String signIn(String username, String password);

    String signUp(String username, String password, String repeatedPassword);

    String makeUserAdmin(String username, String adminUsername, String adminPassword);

    User getByUsername(String username);

    String removeUser(Integer id);
}
