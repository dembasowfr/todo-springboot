package com.example.springboot.controller;

import com.example.springboot.entity.Company;
import com.example.springboot.service.CompanyService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// HttpStatus
import org.springframework.http.HttpStatus;

import java.util.List;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;



@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;


    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // Create a new Company
    @PostMapping
    public ResponseEntity<?> createCompany(@RequestBody Company company, @RequestParam Long user_id) {
        try {

            // Save the company
            Company createdCompany = companyService.createCompany(company, user_id);
            // Add compnay
            return ResponseEntity.ok(createdCompany);
            
        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/companies");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);  // 500 Internal Server Error with error details
        }
    }

    // Get all Companies
    @GetMapping
    public ResponseEntity<?> getAllCompanies(@RequestParam Long user_id) {
        try {
            // Let's get the user role from the request
            List<Company> companies = companyService.getAllCompanies(user_id);
            return ResponseEntity.ok(companies);  // 200 OK with list of companies
        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/companies");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);  // 500 Internal Server Error with error details
        }
    }

    // Get a Company by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long id, @RequestParam Long user_id) {
        try {
            Company company = companyService.getCompanyById(id, user_id);
            return ResponseEntity.ok(company);

        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/companies");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);  // 500 Internal Server Error with error details
        }
       
    }

    // Update an existing Company
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody Company company, @RequestParam Long user_id) {
        try {
            Company updatedCompany = companyService.updateCompany(id, company, user_id);
            return ResponseEntity.ok("Company with id: "+id+ " has been updated successfully ✅");
        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/companies");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);  // 500 Internal Server Error with error details
        }
    }

    // Delete a Company
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id, @RequestParam Long user_id) {
        try {
            companyService.deleteCompany(id, user_id);
            // Return Success message 
            return ResponseEntity.ok("Company with id: "+id+ " has been deleted successfully ✅");
        } catch (RuntimeException e) {
            // Return a detailed error response with the exception message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("path", "/api/companies");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);  // 500 Internal Server Error with error details
        }
    }
}