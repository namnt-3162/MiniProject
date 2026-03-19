package com.example.employee_management.service;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class EmployeeUtils {
    
    public String generateEmployeeCode() {
        return "EMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
