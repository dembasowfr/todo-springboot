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

    // // Create a new User
    public User createUser(User user, Long user_id) {



        //If there's only 1 company,
        // The current user can only create SUPER_USER within 
        // that super company.
        // Otherwise,  if the SUPER USER tries to create an ADMIN USER,
        // or a STANDARD USER, first they will have to create a normal company
        // and then create the user within that company.

        User currentUser = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));
        String user_role = currentUser.getRole();

        switch (user_role) {
            case "SUPER_USER":
                // SUPER_USER can create:
                //1. unlimited SUPER_USER within the default company(only super users can be created in the default company)
                //2. unlimited ADMIN_USER and STANDARD_USER within other companies
                // Check if the company exists
                if(user.getCompany().getId() == 1 && user.getRole() != "SUPER_USER"){
                    throw new RuntimeException("DEFAULT COMPANY can Only Have SUPER USERS💬");
                }
                else if (user.getCompany().getId() != 1 && user.getRole() == "SUPER_USER") {
                    // Save user to the repository
                    throw new RuntimeException("SUPER USERS can only create SUPER USERS for the DEFAULT COMPANY!!!.");
                    //throw new RuntimeException("SUPER USERS can only create users for the default company!!!.");
                }
                else{
                    return userRepository.save(user);
                }
                
            case "ADMIN_USER":
                // Check if the company exists
                // ADMIN_USER can only create users for their company

                // Get admin user's company
                Company adminCompany = currentUser.getCompany();
                if (user.getCompany().getId() != adminCompany.getId()) {
                    throw new RuntimeException("ADMIN USERS can only create users for their own company!!!.");
                }else{
                    return userRepository.save(user);
                }
            case "STANDARD_USER":
                throw new RuntimeException("STANDARD USERS can not create a user!!!.");
            default:
                // Company id does not exist
                throw new RuntimeException("Company with id " + user.getCompany().getId() + " does not exist!!!");
        }
    }


    // Get all Users
    public List<User> getAllUsers(Long user_id) {

        User currentUser = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));
        String user_role = currentUser.getRole();

        switch (user_role) {
            case "SUPER_USER":
                return userRepository.findAll();
            case "ADMIN_USER":
            // Return only the users within this admin's company
                //This has to be list of users
                return currentUser.getCompany().getUsers();

            case "STANDARD_USER":
                return List.of(currentUser);
            default:
                throw new RuntimeException("Only SUPER USERS can view all users!!!");
                
        }
    }


    // Get a User by ID
    public User getUserById(Long id, Long user_id) {

        User currentUser = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));
        String user_role = currentUser.getRole();

        switch(user_role){
            case "SUPER_USER":
                // SUPER USERS can view all users(any user including themselves and other SUPER USERS)
                return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
            case "ADMIN_USER":
                // ADMIN USERS can only view users within their company
                // OR themselves
                return userRepository.findById(id).map(user -> {
                    if (user.getCompany().getId() == currentUser.getCompany().getId() || user.getId() == currentUser.getId()) {
                        return user;
                    } else {
                        throw new RuntimeException("ADMIN USERS can only view THEMSELVES or users within their company!!!");
                    }
                }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            case "STANDARD_USER":
                // STANDARD USERS can only view themselves
                return userRepository.findById(id).map(user -> {
                    if (user.getId() == currentUser.getId()) {
                        return user;
                    } else {
                        throw new RuntimeException("STANDARD USERS can only view THEMSELVES!!!");
                    }
                }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            default:
                throw new RuntimeException("Current User not found with id: " + user_id);
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
