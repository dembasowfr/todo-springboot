package com.example.springboot.controller;

import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;

import java.util.List;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

   
    // Create a new User
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user, @RequestParam Long user_id) {

        try{
            User createdUser = userService.createUser(user, user_id);
            return ResponseEntity.ok(createdUser);

        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", e.getClass().getSimpleName());
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/users");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);  // 500 Internal Server Error with error details
        }
    }



    // Get all Users
    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam Long user_id) {
        try {
            List<User> users = userService.getAllUsers(user_id);
            return ResponseEntity.ok(users);
        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/users");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);  // 500 Internal Server Error with error details
        }
    }

    // Get a User by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id, @RequestParam Long user_id) {
        try {
            User user = userService.getUserById(id, user_id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/users/" + id);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);  // 500 Internal Server Error with error details
        }
    }

    // Update an existing User
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user, @RequestParam Long user_id) {
        try {
            User updatedUser = userService.updateUser(id, user, user_id);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/users/" + id);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);  // 500 Internal Server Error with error details
        }
    }

    // Delete a User
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestParam Long user_id) {
        try {
            userService.deleteUser(id, user_id);
            // Return Success message 
            return ResponseEntity.ok("User with id: "+id+ " has been deleted successfully ✅");
        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/users/" + id);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);  // 500 Internal Server Error with error details
        }
      
    }
}
