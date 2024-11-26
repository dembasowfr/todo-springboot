package com.example.springboot.service;

import com.example.springboot.entity.Task;
import com.example.springboot.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Create a new Task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Get all Tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get a Task by ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Update an existing Task
    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(task);
        }).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    // Delete a Task
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
