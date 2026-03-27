package com.example.employee_management.repository;

import java.util.List;

import com.example.employee_management.model.DeptStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.employee_management.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByNameContaining(String name);
    
    List<Employee> findByDepartmentId(Long deptId);

    @Query("SELECT e.department.name as deptName, COUNT(e) as employeeCount " +
            "FROM Employee e GROUP BY e.department.name")
    List<DeptStatistics> countEmployeesByDept();

    @Query("SELECT COUNT(e) FROM Employee e")
    Long countTotalEmployees();
}
