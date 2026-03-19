package com.example.employee_management;

import com.example.employee_management.model.Employee;
import com.example.employee_management.service.EmployeeUtils;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeUtils employeeUtils;
    private final ModelMapper modelMapper;
    private final List<Employee> employeeList = new ArrayList<>();
    // Constructor Injection (Spring sẽ tự động inject các bean tương ứng)
    public EmployeeController(EmployeeUtils employeeUtils, ModelMapper modelMapper) {
        this.employeeUtils = employeeUtils;
        this.modelMapper = modelMapper;
        employeeList.add(new Employee(employeeUtils.generateEmployeeCode(), "Nguyen Van A", "Developer"));
    }

    @GetMapping("/test-bean")
    public String testBean() {
        String empCode = employeeUtils.generateEmployeeCode();
        boolean isMapperLoaded = (modelMapper != null);
        
        return "New Employee Code: " + empCode + " | ModelMapper is ready: " + isMapperLoaded;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeList);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        if (employee.getId() == null || employee.getId().isEmpty()) {
            employee.setId(employeeUtils.generateEmployeeCode());
        }
        employeeList.add(employee);
        
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }
}
