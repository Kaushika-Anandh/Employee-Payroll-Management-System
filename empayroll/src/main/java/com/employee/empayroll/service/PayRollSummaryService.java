package com.employee.empayroll.service;

import com.employee.empayroll.models.Employee;
import com.employee.empayroll.models.PayRollSummary;
import com.employee.empayroll.models.Salary;
import com.employee.empayroll.models.SummaryId;
import com.employee.empayroll.repository.EmployeeRepository;
import com.employee.empayroll.repository.PayRollSummaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PayRollSummaryService {

    private final EmployeeRepository employeeRepository;
    private final PayRollSummaryRepository payRollSummaryRepository;

    public PayRollSummaryService(EmployeeRepository employeeRepository, PayRollSummaryRepository payRollSummaryRepository) {
        this.employeeRepository = employeeRepository;
        this.payRollSummaryRepository = payRollSummaryRepository;
    }

    public void addSummaryEntry(Salary salary){

        String empId = salary.getId().getEmpId();
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmpId(empId);
        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            SummaryId summaryId = new SummaryId(
                    empId,employee.getName(),
                    employee.getRoleName(),
                    salary.getId().getMonth(),
                    salary.getId().getYear());
            PayRollSummary summary = new PayRollSummary(summaryId,salary.getMonthlyPay());
            payRollSummaryRepository.save(summary);
        }
    }

    //payroll summaries
    public List<PayRollSummary> displayPayRollSummaries(){
        return payRollSummaryRepository.findAll();
    }

    //summary of employee
    public List<PayRollSummary> displayEmployeePayRollSummary(String empId){
        return payRollSummaryRepository.findPayRollSummariesById_EmpId(empId);
    }

    //annual summary of employee
    public List<PayRollSummary> displayEmployeeAnnualPayRollSummary(String empId, Integer year) {
        return payRollSummaryRepository.findPayRollSummariesById_EmpIdAndId_Year(empId,year);
    }

    //get total of all employees within given year
    public String getTotalPayrollSummaryOfYear(Integer year){
        List<PayRollSummary> summaries = payRollSummaryRepository.findPayRollSummariesById_Year(year);
        Double summaryTotal = 0.0;
        for (PayRollSummary summary : summaries) {
            summaryTotal += summary.getMonthlyPay();
        }
        return "Annual Payroll Summary of "+year+": " + summaryTotal;
    }

    //get total of all employees within given year
    public String getTotalPayrollSummaryOfMonth(String month){
        List<PayRollSummary> summaries = payRollSummaryRepository.findPayRollSummariesById_Month(month);
        Double summaryTotal = 0.0;
        for (PayRollSummary summary : summaries) {
            summaryTotal += summary.getMonthlyPay();
        }
        return "Annual Payroll Summary of "+month+": " + summaryTotal;
    }

    public String getAnnualPayrollSummaryOfEmployee(String empId,Integer year){
        List<PayRollSummary> summaries = payRollSummaryRepository.findPayRollSummariesById_EmpIdAndId_Year(empId,year);
        Double summaryTotal = 0.0;
        for (PayRollSummary summary : summaries) {
            summaryTotal += summary.getMonthlyPay();
        }
        return "Annual Payroll Summary of "+year+" for employee "+empId+": " + summaryTotal;
    }
}
