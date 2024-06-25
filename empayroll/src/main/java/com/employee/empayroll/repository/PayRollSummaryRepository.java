package com.employee.empayroll.repository;

import com.employee.empayroll.models.PayRollSummary;
import com.employee.empayroll.models.SummaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PayRollSummaryRepository extends JpaRepository<PayRollSummary, String> {

    List<PayRollSummary> findPayRollSummariesById_EmpId(String empId);
    List<PayRollSummary> findPayRollSummariesById_Year(Integer year);
    List<PayRollSummary> findPayRollSummariesById_Month(String month);
    List<PayRollSummary> findPayRollSummariesById_EmpIdAndId_Year(String empId, Integer year);

}
