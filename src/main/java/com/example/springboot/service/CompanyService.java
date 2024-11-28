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
    public Company updateCompany(Long id, Company updatedCompany) {
        return companyRepository.findById(id).map(company -> {
            company.setName(updatedCompany.getName());
            company.setAddress(updatedCompany.getAddress());
            company.setPhoneNumber(updatedCompany.getPhoneNumber());
            company.setEmail(updatedCompany.getEmail());
            company.setWebsite(updatedCompany.getWebsite());

            return companyRepository.save(company);

        }).orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }

    // Delete a Company
    public void deleteCompany(Long id) {
        try {
            companyRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
