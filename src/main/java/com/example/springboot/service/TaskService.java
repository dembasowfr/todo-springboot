package com.example.springboot.service;

import com.example.springboot.entity.Task;
import com.example.springboot.repository.TaskRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.repository.CompanyRepository;
import com.example.springboot.entity.User;
import com.example.springboot.entity.Company;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;



import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    
    // Define current user
    private User currentUser;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       CompanyRepository companyRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;

    }

    // Create a new Task
    public Task createTask(Task task, Long user_id) {
        // Fetch the current user
        User currentUser = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));
        String user_role = currentUser.getRole();

        switch (user_role) {
            case "SUPER_USER":
                // SUPER_USER can assign tasks to themselves, other SUPER USERS, ADMIN_USERS, and STANDARD_USERS in any company
                return taskRepository.save(task);

            case "ADMIN_USER":
                // Handle if ADMIN_USER try to assign
                if (task.getUser() != null && task.getUser().getId() != null) {
                    // Fetch the assigned user
                    User assignedUser = userRepository.findById(task.getUser().getId())
                            .orElseThrow(() -> new RuntimeException("Assigned user not found with id: " + task.getUser().getId()));
                    
                    // Check if the assigned user is in the same company
                    if (assignedUser.getCompany().getId().equals(currentUser.getCompany().getId())) {
                        return taskRepository.save(task);
                    } else {
                        throw new RuntimeException("ADMIN USER can only assign tasks to THEMSELVES or STANDARD USERS within the same company!!!");
                    }
                } else {
                    // Assign task to the ADMIN_USER themselves
                    task.setUser(currentUser);
                    return taskRepository.save(task);
                }

            case "STANDARD_USER":
                // STANDARD_USER can only assign tasks to themselves
                if (task.getUser() != null && !task.getUser().getId().equals(currentUser.getId())) {
                    throw new RuntimeException("STANDARD USER can only assign tasks to themselves!!!");
                }
                task.setUser(currentUser);
                return taskRepository.save(task);

            default:
                throw new RuntimeException("Invalid user role: " + user_role);
        }
    }


   // Get all Tasks
    public List<Task> getAllTasks(Long user_id) {

        User currentUser = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));
        String user_role = currentUser.getRole();

        Company currentCompany = currentUser.getCompany();

        switch (user_role) {
            case "SUPER_USER":
                return taskRepository.findAll();

            case "ADMIN_USER":
                return taskRepository.findAll((root, query, criteriaBuilder) -> {
                    Predicate ownTasks = criteriaBuilder.equal(root.get("user"), currentUser);
                    Predicate companyTasks = criteriaBuilder.equal(root.get("user").get("company"), currentCompany);
                    return criteriaBuilder.or(ownTasks, companyTasks);
                });

            case "STANDARD_USER":
                return taskRepository.findAll((root, query, criteriaBuilder) -> 
                    criteriaBuilder.equal(root.get("user"), currentUser)
                );

            default:
                throw new RuntimeException("Invalid user role: " + user_role);
        }
    }


    // Get a Task by ID
    public Task getTaskById(Long id, Long user_id) {
        User currentUser = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));
        String user_role = currentUser.getRole();


        switch (user_role) {
            case "SUPER_USER":
                return  taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

            case "ADMIN_USER":
                // ADMIN_USER can only view tasks within their company
                // Or tasks assigned to themselves
                return taskRepository.findById(id).map(task -> {
                    if (task.getUser().getCompany().getId().equals(currentUser.getCompany().getId())
                            || task.getUser().getId().equals(currentUser.getId())) {
                        return task;
                    }
                    throw new RuntimeException("ADMIN USER can only view tasks within their company!!!.");
                }).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

            case "STANDARD_USER":
                // STANDARD_USER can only view their own tasks
                return taskRepository.findById(id).map(task -> {
                    if (task.getUser().getId().equals(currentUser.getId())) {
                        return task;
                    }
                    throw new RuntimeException("STANDARD USER can only view their own tasks!!!");
                }).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

            default:
                throw new RuntimeException("Invalid user role: " + user_role);
        }
    }

    // Update an existing Task
   public Task updateTask(Long id, Task updatedTask, Long user_id) {
        User currentUser = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));
        String user_role = currentUser.getRole();
  

        switch (user_role) {
            case "SUPER_USER":
                return taskRepository.findById(id).map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.isCompleted());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

            case "ADMIN_USER":
                // ADMIN_USER can only update tasks within their company
               return taskRepository.findById(id).map(task -> {
                    // here already the admin user will not be able 
                    // to view this task if it is not in the same company
                    if (task.getUser().getCompany().getId().equals(currentUser.getCompany().getId())) {
                        task.setTitle(updatedTask.getTitle());
                        task.setDescription(updatedTask.getDescription());
                        task.setCompleted(updatedTask.isCompleted());
                        return taskRepository.save(task);
                    }
                    else{
                        throw new RuntimeException("ADMIN USER can only update tasks within their company!!!.");
                    }
                }).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

            case "STANDARD_USER":
                // STANDARD_USER can only update their own tasks
                return taskRepository.findById(id).map(task -> {
                    if (task.getUser().getId().equals(currentUser.getId())) {
                        task.setTitle(updatedTask.getTitle());
                        task.setDescription(updatedTask.getDescription());
                        task.setCompleted(updatedTask.isCompleted());
                        return taskRepository.save(task);
                    }
                    else{
                        throw new RuntimeException("STANDARD USER can only update their own tasks!!!");
                    }
                }).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

            default:
                throw new RuntimeException("Invalid user role: " + user_role);
        }
    }

    

    // Delete a Task
   public void deleteTask(Long id, Long user_id) {
        User currentUser = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));
        String user_role = currentUser.getRole();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        switch (user_role) {
            case "SUPER_USER":
                taskRepository.deleteById(id);
                break;

            case "ADMIN_USER":
                if (task.getUser().getCompany().getId() == currentUser.getCompany().getId()) {
                    taskRepository.deleteById(id);
                } else {
                    throw new RuntimeException("ADMIN USER can only delete tasks within their company.");
                }
                break;

            case "STANDARD_USER":
                if (task.getUser().getId() == currentUser.getId()) {
                    taskRepository.deleteById(id);
                } else {
                    throw new RuntimeException("STANDARD USER can only delete their own tasks.");
                }
                break;

            default:
                throw new RuntimeException("Invalid user role: " + user_role);
        }
    }
    
}
