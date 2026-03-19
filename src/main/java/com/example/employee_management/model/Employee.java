package com.example.employee_management.model;

public class Employee {
    private String id;
    private String name;
    private String position;

    // Constructors
    public Employee() {}
    public Employee(String id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    // Getters and Setters (Quan trọng để Spring có thể parse JSON)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
}