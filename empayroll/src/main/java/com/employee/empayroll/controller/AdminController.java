package com.employee.empayroll.controller;

import com.employee.empayroll.models.Employee;
import com.employee.empayroll.models.Grade;
import com.employee.empayroll.models.NetPay;
import com.employee.empayroll.service.EmployeeService;
import com.employee.empayroll.service.GradeService;
import com.employee.empayroll.service.NetPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/admin")
public class AdminController {

    public final GradeService gradeService;
    private final EmployeeService employeeService;
    private final NetPayService netPayService;

    @Autowired
    public AdminController(GradeService gradeService, EmployeeService employeeService, NetPayService netPayService) {
        this.gradeService = gradeService;
        this.employeeService = employeeService;
        this.netPayService = netPayService;
    }

    //Grade Controls of Admin
    @GetMapping(path = "grades")
    public List<Grade> getGrades(){
        return gradeService.getGrades();
    }

    @PutMapping(path = "grades/{gradeId}")
    public void updateGrades(
            @PathVariable("gradeId") Integer gradeId,
            @RequestParam(required = false)Integer hraPercentage,
            @RequestParam(required = false)Integer daPercentage,
            @RequestParam(required = false)Integer maPercentage){
        System.out.println("UPDATE request received for Grade number :"+gradeId);
        gradeService.updateGrade(gradeId,hraPercentage,daPercentage,maPercentage);
    }

    //Employee Controls of Admin
    @GetMapping(path = "employee")
    public List<Employee> getEmployees(){
        return employeeService.getAllEmployees();
    }

    @PostMapping(path = "employee")
    public void registerNewEmployee(
            @RequestBody Employee employee){
        employeeService.addEmployee(employee);
    }

    @DeleteMapping(path = "employee/{empId}")
    public void  deleteEmployee(
            @PathVariable("empId") String empId){
        employeeService.deleteEmployee(empId);
    }

    @PutMapping(path = "employee/{empId}")
    public void updateStudent(
            @PathVariable("empId") String empId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) Integer empGrade,
            @RequestParam(required = false) Double basicPay){
        System.out.println("PUT request received for employee");
        employeeService.updateEmployee(empId,name,email,roleName,empGrade,basicPay);
    }

    //Admin view of NetPayInfo
    @GetMapping(path = "netpay")
    public List<NetPay> getNetPay(){
        return netPayService.getAllNeyPay();
    }


}
