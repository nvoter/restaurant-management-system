package com.developer.restaurant_management_system.services.implementations;

import com.developer.restaurant_management_system.models.Role;
import com.developer.restaurant_management_system.models.User;
import com.developer.restaurant_management_system.repositories.UserRepository;

import com.developer.restaurant_management_system.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
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

    public String signIn(String username, String password) {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            return "ERROR: User not found";
        }
        if (!user.getPassword().equals(password)) {
            return "ERROR: Incorrect password";
        }
        return "Sign in successfully";
    }

    public String signUp(String username, String password, String repeatedPassword) {
        if (userRepository.getByUsername(username) != null) {
            return "ERROR: Username is already taken";
        }
        if (!password.equals(repeatedPassword)) {
            return "ERROR: Password mismatch";
        }
        User user = new User(username, password);
        userRepository.save(user);
        return "User successfully signed up";
    }

    @Transactional
    public String makeUserAdmin(String username, String adminUsername, String adminPassword) {
        String loginResponse = login(adminUsername, adminPassword);
        if (!loginResponse.contains("ERROR")) {
            if (userRepository.getByUsername(username) == null) {
                return "ERROR: User does not exist";
            }
            if (userRepository.getByUsername(username).getRole() == Role.ADMIN) {
                return "ERROR: User is already an admin";
            }
            User user = userRepository.getByUsername(username);
            user.setRole(Role.ADMIN);
            userRepository.save(user);
            return "The user has been successfully assigned as an admin";
        } else {
            return loginResponse;
        }
    }

    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Transactional
    public String removeUser(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return "ERROR: User to delete not found";
        }
        userRepository.deleteById(id);
        return "User successfully deleted";
    }
}