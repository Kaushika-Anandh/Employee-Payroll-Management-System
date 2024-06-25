package com.employee.empayroll.controller;

import com.employee.empayroll.models.Grade;
import com.employee.empayroll.models.PayRollSummary;
import com.employee.empayroll.service.PayRollSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/accountant")
public class AccountantController {

    @Autowired
    public final PayRollSummaryService payRollSummaryService;

    public AccountantController(PayRollSummaryService payRollSummaryService) {
        this.payRollSummaryService = payRollSummaryService;
    }

    @GetMapping
    public List<PayRollSummary> getPayRollSummaries(){
        return payRollSummaryService.displayPayRollSummaries();
    }

    @GetMapping(path = "{empId}")
    public List<PayRollSummary> getEmployeePayRollSummary(@PathVariable("empId") String empId){
        return payRollSummaryService.displayEmployeePayRollSummary(empId);
    }

    @GetMapping(path = "{empId}/{year}")
    public List<PayRollSummary> getEmployeeAnnualPayRollSummary(@PathVariable("empId") String empId,@PathVariable("year") Integer year){
        return payRollSummaryService.displayEmployeeAnnualPayRollSummary(empId,year);
    }

    @GetMapping(path = "Summary/Annual/{year}")
    public String getAnnualPayRollSummaries(@PathVariable("year") Integer year){
        return payRollSummaryService.getTotalPayrollSummaryOfYear(year);
    }

    @GetMapping(path = "Summary/Monthly/{month}")
    public String getMonthlyPayRollSummaries(@PathVariable("month") String month){
        return payRollSummaryService.getTotalPayrollSummaryOfMonth(month);
    }

    @GetMapping(path = "annualSummary/{year}/{empId}")
    public String getAnnualPayRollSummaryOfEmployee(@PathVariable("year") Integer year,@PathVariable("empId") String empId){
        return payRollSummaryService.getAnnualPayrollSummaryOfEmployee(empId,year);
    }
}
