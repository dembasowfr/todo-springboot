package com.example.springboot.service;

import com.example.springboot.entity.Company;
import com.example.springboot.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // Create a new Company
    public Company createCompany(Company company) {
        try {
            return companyRepository.save(company);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    // Get all Companies
    public List<Company> getAllCompanies() {
        try {
            return companyRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    // Get a Company by ID
    public Optional<Company> getCompanyById(Long id) {
        try{
            return companyRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
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
