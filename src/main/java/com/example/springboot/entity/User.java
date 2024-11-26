package com.example.springboot.entity;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "\"users\"") // user is a reserved keyword in H2 database
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Add name field here
    private String surname; // Add surname field here

    private String username;
    private String role; // STANDARD, COMPANY_ADMIN, SUPER_USER
    private String email; // Add email field here

   

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @JsonBackReference // Prevent circular reference when serializing User -> Company -> User
    private Company company;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;


    // Constructors
    public User() {}

    public User(String name, String surname, String username, String role, String email, Company company) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.role = role;
        this.email = email;
        this.company = company;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; } // Getter for name
    public void setName(String name) { this.name = name; } // Setter for name

    public String getSurname() { return surname; } // Getter for surname
    public void setSurname(String surname) { this.surname = surname; } // Setter for surname

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; } // Getter for email
    public void setEmail(String email) { this.email = email; } // Setter for email

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }
}
