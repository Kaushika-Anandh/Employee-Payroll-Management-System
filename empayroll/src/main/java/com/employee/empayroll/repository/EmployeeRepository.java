package com.employee.empayroll.repository;

import com.employee.empayroll.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

    Optional<Employee> findEmployeeByEmpId(String empId);
    Optional<Employee> findEmployeeByEmail(String email);
    List<Employee> findAllByEmpGrade(Integer gradeId);
}
