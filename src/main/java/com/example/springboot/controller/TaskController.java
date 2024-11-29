package com.example.springboot.controller;

import com.example.springboot.entity.Task;
import com.example.springboot.entity.User;
import com.example.springboot.entity.Company;

import com.example.springboot.service.TaskService;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;

import java.util.List;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks(@RequestParam Long user_id) {
        try{
            return taskService.getAllTasks(user_id);

        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/tasks");
            return null;
        }
    }

    // Create a new Task
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task, @RequestParam Long user_id) {
        try{
            Task createdTask = taskService.createTask(task, user_id);
            return ResponseEntity.ok(createdTask);

        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", e.getClass().getSimpleName());
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/tasks");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);  // 500 Internal Server Error with error details
        }
    }


    // Get a Task by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id, @RequestParam Long user_id) {
        try{
           
            Task task = taskService.getTaskById(id, user_id);
            return ResponseEntity.ok(task);

        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/tasks/" + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Update an existing Task
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task, @RequestParam Long user_id) {
        try{
            
            Task updatedTask = taskService.updateTask(id, task, user_id);
            return ResponseEntity.ok(updatedTask);

        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/tasks/" + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /*// Delete a Task
    @DeleteMapping("/{id}   ")
    public void deleteTask(@PathVariable Long id, @RequestParam Long user_id) {
        try{
            taskService.deleteTask(id, user_id);

        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/tasks/" + id);
        }
    }

    */
}
