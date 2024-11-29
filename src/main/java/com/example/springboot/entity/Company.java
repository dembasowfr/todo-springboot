package com.example.springboot.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;



import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "\"companies\"") // company is a reserved keyword in H2 database
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address; // Add address field
    private String email; // Add email field
    private String phoneNumber; // Add phone number field
    private String website; // Add website field

    // company also has creator:
    //private String creatorId; // Add creator field

    // Here if the creator is not given in the 
    // request, the default creator will be the SUPER_USER



    @OneToMany(mappedBy = "company")
    @JsonManagedReference // Serialize the users associated with the company
    private List<User> users;


    // Default constructor (required by JPA if using Hibernate or similar ORM)
    public Company() {}

    // Parameterized constructor
    public Company(String name, String address, String phoneNumber, String email, String website) {
  
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.website = website;
        this.users = new ArrayList<>();

    }
    

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; } // Getter for address
    public void setAddress(String address) { this.address = address; } // Setter for address

    // Add email field here
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


    public String getPhoneNumber() { return phoneNumber; } // Getter for phoneNumber
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; } // Setter for phoneNumber

    public String getWebsite() { return website; } // Getter for website
    public void setWebsite(String website) { this.website = website; } // Setter for website

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }
}
