package com.example.springboot.service;

import com.example.springboot.entity.Company;
import com.example.springboot.repository.CompanyRepository;
import com.example.springboot.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.springboot.entity.User;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    // Create a new Company
    public Company createCompany(Company company, Long user_id) {

        User currentUser = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));

        String user_role = currentUser.getRole();

        switch (user_role) {
            case "SUPER_USER":
                return companyRepository.save(company); 
            default:
                throw new RuntimeException("Only SUPER USERS can create a company!!!");
        }
    }

    // Get all Companies
    // I want to return the response body as well as the status code
    public List<Company> getAllCompanies(Long user_id) {

        User currentUser = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));
        String user_role = currentUser.getRole();

        switch (user_role) {
            case "SUPER_USER":
                return companyRepository.findAll();
            case "ADMIN_USER":
            // Return only the company of this admin.
                return List.of(currentUser.getCompany());
            default:
                throw new RuntimeException("Only SUPER USERS can view all companies!!!");
              
        }
    }

    // Get a Company by ID
    public Company getCompanyById(Long company_id, Long user_id) {

        //Get user role by user_id
        //String user_role =
        User currentUser = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));
        String user_role = currentUser.getRole();

        switch (user_role) {
            case "SUPER_USER":
                return companyRepository.findById(company_id).orElseThrow(() -> new RuntimeException("Company not found with id: " + company_id));
            case "ADMIN_USER":
                // here return only the companies created by the ADMIN_USER
                // Here if this admin belongs to this company, then return the company
                if (currentUser.getCompany().getId() == company_id) {
                    return companyRepository.findById(company_id).orElseThrow(() -> new RuntimeException("Company not found with id: " + company_id));
                }
                else {
                    throw new RuntimeException("ADMIN USERS can only view their own company!!!"); 
                }
            case "STANDARD_USER":
                throw new RuntimeException("STANDARD USERS can not view any company!!!"); 
    
            default:
                throw new RuntimeException("Company with id" + company_id + " does not exist!!!");
        }
        
    }

    // Update an existing Company
    public Company updateCompany(Long id, Company updatedCompany, Long user_id) {
        //Get user role by user_id
        User currentUser = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));
        String user_role = currentUser.getRole();

        // here SUPER_USER can update any company
        // ADMIN_USER can only update the company they belong to
        // STANDARD_USER can not update any company

        switch (user_role) {
            case "SUPER_USER":
                Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
                company.setName(updatedCompany.getName());
                company.setAddress(updatedCompany.getAddress());
                company.setEmail(updatedCompany.getEmail());
                company.setPhoneNumber(updatedCompany.getPhoneNumber());
                company.setWebsite(updatedCompany.getWebsite());
                return companyRepository.save(company);
            case "ADMIN_USER":
                // here if this admin belongs to this company, then update the company
                if (currentUser.getCompany().getId() == id) {
                    Company company1 = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
                    company1.setName(updatedCompany.getName());
                    company1.setAddress(updatedCompany.getAddress());
                    company1.setEmail(updatedCompany.getEmail());
                    company1.setPhoneNumber(updatedCompany.getPhoneNumber());
                    company1.setWebsite(updatedCompany.getWebsite());
                    return companyRepository.save(company1);
                }
                else {
                    throw new RuntimeException("ADMIN USERS can only update their own company!!!"); 
                }
            case "STANDARD_USER":
                throw new RuntimeException("STANDARD USERS can not update any company!!!"); 
            default:
                throw new RuntimeException("Company with id" + id + " does not exist!!!");
        }
    }

    // Delete a Company
    public void deleteCompany(Long id, Long user_id) {
        // SUPER_USER can delete any companies that belong to other users only
        // other users can not delete any company
        // Get user role by user_id
        User currentUser = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));
        String user_role = currentUser.getRole();

        switch(user_role) {
            case "SUPER_USER":
                // The company id should be different from the current user's company id
                if (currentUser.getCompany().getId() == id) {
                    throw new RuntimeException("SUPER USERS can not delete their own company!!!");
                }
                else {
                    // Check if the company exists before attempting to delete
                    companyRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
                    companyRepository.deleteById(id); // Perform the delete
                    // Refresh the database
                    //companyRepository.flush();
                    // if seccessful, return a message
                    break;
                    //throw new RuntimeException("Company with id " + id + " has been deleted successfully!!!");
                }
                //break;
            default:
                throw new RuntimeException("Only SUPER USERS can delete a company!!!");
        }
    }
}
