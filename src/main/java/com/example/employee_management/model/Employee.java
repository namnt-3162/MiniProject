package com.example.employee_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String position;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    // Constructors
    public Employee() {}
    public Employee(Long id, String name, String position, Department department) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.department = department;
    }

    // Getters and Setters (Quan trọng để Spring có thể parse JSON)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}