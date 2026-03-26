package com.example.employee_management.controller;

import com.example.employee_management.exception.EmployeeNotFoundException;
import com.example.employee_management.model.Department;
import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.repository.EmployeeRepository;
import com.example.employee_management.service.EmployeeService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

   public EmployeeController(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeService = employeeService;
    }

    // Lấy tất cả hoặc tìm kiếm theo tên
    @GetMapping
    public List<Employee> getAll(@RequestParam(required = false) String name) {
        if (name != null) return employeeRepository.findByNameContaining(name);
        return employeeRepository.findAll();
    }

   @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody Employee employee) {
        logger.info("Process to create employee: {}", employee.getName());
        try {
            if (employee.getDepartment() != null) {
                Department dept = departmentRepository.findById(employee.getDepartment().getId())
                    .orElseThrow(() -> new RuntimeException("Department not found!"));
            
                employee.setDepartment(dept);
            }
        
            Employee savedEmployee = employeeRepository.save(employee);
            logger.info("Employee created successfully: {}", savedEmployee.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
        } catch (Exception e) {
            logger.error("Error occurred while creating employee: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/dept/{deptId}")
    public List<Employee> getByDept(@PathVariable Long deptId) {
        return employeeRepository.findByDepartmentId(deptId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
    Employee emp = employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + id));
    return ResponseEntity.ok(emp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.warn("Warning: Deleting employee with ID: {}", id);
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/report/count")
    public String getReportCount() {
        return "Employees: " + employeeService.getTotalEmployees();
    }
}