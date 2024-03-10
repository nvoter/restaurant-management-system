package com.developer.restaurant_management_system.controllers;

import com.developer.restaurant_management_system.models.User;
import com.developer.restaurant_management_system.services.implementations.UserServiceImpl;
import com.developer.restaurant_management_system.services.interfaces.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestParam String username, @RequestParam String password) {
        String response = userService.signIn(username, password);
        if (response.equals("Sign in successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestParam String username, @RequestParam String password, @RequestParam String repeatedPassword) {
        String response = userService.signUp(username, password, repeatedPassword);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username) {
        User user = userService.getByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{username}/make-admin")
    public ResponseEntity<String> makeUserAdmin(@PathVariable String username, @RequestParam String adminUsername, @RequestParam String adminPassword) {
        String response = userService.makeUserAdmin(username, adminUsername, adminPassword);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> removeUser(@PathVariable Integer id) {
        String response = userService.removeUser(id);
        if (response.contains("successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}