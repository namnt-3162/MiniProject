package com.example.employee_management.controller;

import java.util.List;

import com.example.employee_management.model.DeptStatistics;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.repository.EmployeeRepository;

import org.springframework.ui.Model;
@Controller
@RequestMapping("/employees")
public class EmployeeWebController {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeWebController.class);

    public EmployeeWebController(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/list")
    public String listEmployees(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "deptId", required = false) Long deptId,
            Model model) {
        
        List<Employee> list;
        
        if (keyword != null && !keyword.isEmpty()) {
            list = employeeRepository.findByNameContaining(keyword);
        } else if (deptId != null) {
            list = employeeRepository.findByDepartmentId(deptId);
        } else {
            list = employeeRepository.findAll();
        }

        model.addAttribute("employees", list);
        model.addAttribute("departments", departmentRepository.findAll()); //dropdown search
        model.addAttribute("keyword", keyword);
        
        return "employee-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {       
        logger.info("Request create employee: Name={}, Email={}", 
                    employee.getName(), employee.getEmail());

        try {
            if (employee.getDepartment() != null) {
                employee.setDepartment(departmentRepository.findById(employee.getDepartment().getId()).orElse(null));
            }
            Employee saved = employeeRepository.save(employee);
            logger.info("Successfully saved new employee with ID: {}", saved.getId());
            
        } catch (Exception e) {
            logger.error("Error occurred while saving employee from web interface: {}", e.getMessage());
            throw e; 
        }
        return "redirect:/employees/list";
    }

    @GetMapping("/statistics")
    public String getStatistics(Model model) {
        // Lấy dữ liệu từ Repository
        List<DeptStatistics> deptStats = employeeRepository.countEmployeesByDept();
        Long totalEmployees = employeeRepository.countTotalEmployees();

        // Đưa vào Model để Thymeleaf sử dụng
        model.addAttribute("deptStats", deptStats);
        model.addAttribute("totalEmployees", totalEmployees);

        return "employee-statistics"; // Trả về file employee-statistics.html
    }
}