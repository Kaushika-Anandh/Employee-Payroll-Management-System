package com.employee.empayroll.repository;

import com.employee.empayroll.models.NetPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NetPayRepository extends JpaRepository<NetPay, String> {
    Optional<NetPay> findNetPayByEmpId(@Param("empId") String empId);
}
