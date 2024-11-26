package com.example.springboot.service;

import com.example.springboot.entity.User;
import com.example.springboot.entity.Company;

import com.example.springboot.repository.UserRepository;
import com.example.springboot.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public UserService(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;

        // Initialize default super user and company
        initializeDefaultData();
    }

    // Initialize default company and user
    private void initializeDefaultData() {
        // Check if default company exists
        if (companyRepository.count() == 0) {
            Company defaultCompany = new Company(
                
                "Sports Unity",
                "Madrid Spain",
                "+31123456789",
                "sportsunity@gmail.com",
                "https://www.sportsunity.com"
            );
            
            companyRepository.save(defaultCompany);

            // Add a default Super User to the default company
            User superUser = new User(
                "Jorge",
                "Lopez",
                "superadmin",
                "SUPER_USER",
                "superadmin@example.com",
                defaultCompany
            );
            userRepository.save(superUser);
        }
    }

    // Create a new User
    public User createUser(User user) {
        try {
            // Check if the company exists
            if (user.getCompany() != null && !companyRepository.existsById(user.getCompany().getId())) {
                throw new RuntimeException("Company with ID " + user.getCompany().getId() + " does not exist.");
            }

            // Save user to the repository
            return userRepository.save(user);
        }
        catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        
    }

    // Get all Users
    public List<User> getAllUsers() {
        try {
            // Return all Users (Give a message if there are no Users)
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    // Get a User by ID
    public Optional<User> getUserById(Long id) {
        try{
            // Return a User by ID (Give a message if the User is not found)
            if (!userRepository.findById(id).isPresent()) {
                throw new RuntimeException("User not found with id: " + id);
            }
            else{
                return userRepository.findById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }

    }



    // Update an existing User
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setSurname(updatedUser.getSurname());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            user.setCompany(updatedUser.getCompany());

            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Delete a User
    public void deleteUser(Long id) {
        try{
            // Delete a User by ID (Give a message if the User is not found)
            if (!userRepository.findById(id).isPresent()) {
                throw new RuntimeException("User not found with id: " + id);
            }
            else{
                userRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
