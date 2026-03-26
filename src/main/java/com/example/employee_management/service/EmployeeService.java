package com.example.employee_management.service;

import org.springframework.stereotype.Service;

import com.example.employee_management.repository.EmployeeRepository;

import org.springframework.cache.annotation.Cacheable;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Cacheable(value = "employeeCount")
    public long getTotalEmployees() {
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        return employeeRepository.count();
    }
}
