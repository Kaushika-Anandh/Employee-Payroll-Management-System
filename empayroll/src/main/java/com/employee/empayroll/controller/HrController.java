package com.employee.empayroll.controller;

import com.employee.empayroll.models.Salary;
import com.employee.empayroll.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/hr")
public class HrController {

    private final SalaryService salaryService;

    @Autowired
    public HrController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping
    public List<Salary> getAllSalaries(){
        return salaryService.getAllSalary();
    }

    @PostMapping
    public void addNewSalary(
            @RequestParam String empId,
            @RequestParam String month,
            @RequestParam Integer year,
            @RequestParam Integer workingDays){
        salaryService.addSalaryEntry(empId, month, year, workingDays);
    }

    @PutMapping(path = "{empId}")
    public void updateSalary(
            @PathVariable("empId") String empId,
            @RequestParam(required = true) String month,
            @RequestParam(required = true) Integer year,
            @RequestParam(required = false) Integer workingDays){
        System.out.println("UPDATE request received for "+empId+" in "+month+" "+year);
        salaryService.updateSalaryEntry(empId, month, year, workingDays);
    }


}


