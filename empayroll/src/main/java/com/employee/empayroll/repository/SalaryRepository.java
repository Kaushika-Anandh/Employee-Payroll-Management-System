package com.employee.empayroll.repository;

import com.employee.empayroll.models.Salary;
import com.employee.empayroll.models.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, String> {
    Optional<Salary> findSalaryById (SalaryId salaryId);
}
