package com.employee.empayroll.service;

import com.employee.empayroll.models.*;
import com.employee.empayroll.repository.EmployeeRepository;
import com.employee.empayroll.repository.NetPayRepository;
import com.employee.empayroll.repository.SalaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {

    private final NetPayRepository netPayRepository;
    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;
    private final PayRollSummaryService payRollSummaryService;

    public SalaryService(NetPayRepository netPayRepository, SalaryRepository salaryRepository, EmployeeRepository employeeRepository, PayRollSummaryService payRollSummaryService) {
        this.netPayRepository = netPayRepository;
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
        this.payRollSummaryService = payRollSummaryService;
    }

    public Double calculateSalary(String empId, Integer workingDays){
        Optional<NetPay> existingNetPay = netPayRepository.findNetPayByEmpId(empId);
        if(existingNetPay.isPresent()){
            NetPay netPay = existingNetPay.get();
            Double perDayPay = netPay.getPerDayPay();
            return perDayPay * workingDays;
        }
        else{
            throw new RuntimeException("No NetPay found for employee id " + empId);
        }
    }
    //display all values in NetPay Table
    public List<Salary> getAllSalary(){
        return salaryRepository.findAll();
    }

    public void addSalaryEntry(String empId, String month, Integer year, Integer workingDays){
        if(!isValidMonth(month)){
            throw new IllegalArgumentException("Invalid month: "+month);
        }
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmpId(empId);
        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            //SalaryId salaryId = new SalaryId(empId, month, year);
            Salary salary = new Salary(employee, month,year,workingDays);
            //salary.setId(salaryId);
            //salary.setWorkingDays(workingDays);
            Double monthlySalary = calculateSalary(empId,workingDays);
            salary.setMonthlyPay(monthlySalary);

            salaryRepository.save(salary);
            payRollSummaryService.addSummaryEntry(salary);
        }else {
            throw new RuntimeException("No employee found for employee id " + empId);
        }
    }

    private boolean isValidMonth(String month) {
        try{
            EmpMonths.valueOf(month);
            return true;
        }catch(IllegalArgumentException e){
            return false;
        }
    }

    public void updateSalaryEntry(String empId, String month, Integer year, Integer workingDays){
        if (!isValidMonth(month)) {
            throw new IllegalArgumentException("Invalid month");
        }

        SalaryId salaryId = new SalaryId(empId, month, year);
        Optional<Salary> salaryOptional = salaryRepository.findSalaryById(salaryId);

        if(salaryOptional.isPresent()){

            Salary salary = salaryOptional.get();
            if (workingDays!=null && !workingDays.equals(salary.getWorkingDays())) {
                salary.setWorkingDays(workingDays);
                salary.setMonthlyPay(calculateSalary(empId,workingDays));
            }
                salaryRepository.save(salary);

            }else {
            throw new RuntimeException("No salary entry found for employee id " + empId + " for month " + month + " and year " + year);
        }
    }
}
