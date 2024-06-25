package com.employee.empayroll.service;

import com.employee.empayroll.models.Employee;
import com.employee.empayroll.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final NetPayService netPayService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, NetPayService netPayService){
        this.employeeRepository = employeeRepository;
        this.netPayService = netPayService;
    }

    //viewing details (get)
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //adding new employee (post)
    @Transactional
    public void addEmployee(Employee employee){
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmpId(employee.getEmpId());
        if (employeeOptional.isPresent()){
            throw new IllegalStateException("employee already exists");
        }
        employeeRepository.save(employee);
        netPayService.createNetPayField(employee);
    }

    //deleting existing employee (delete)
    public void deleteEmployee(String empId){
        Employee employee = employeeRepository.findEmployeeByEmpId(empId).orElseThrow(() ->
                new IllegalStateException("employee does not exist"));
        netPayService.deleteNetPay(empId);
        employeeRepository.delete(employee);
    }

    //updating employee details (put)
    @Transactional
    public void updateEmployee(String empId,
                               String name,
                               String email,
                               String roleName,
                               Integer empGrade,
                               Double basicPay){
        Employee employee = employeeRepository.findEmployeeByEmpId(empId)
                .orElseThrow(() -> new IllegalStateException("employee does not exist"));

        boolean isEmpGradeUpdated = false;
        boolean isBasicPayUpdated = false;

        if (empId != null && !empId.isEmpty() &&
        !Objects.equals(employee.getEmpId(),empId)){
            Optional<Employee> employeeOptionalEmpId = employeeRepository.findEmployeeByEmpId(empId);
            if(employeeOptionalEmpId.isPresent()){
                throw new IllegalStateException("employee ID taken");
            }
            employee.setEmpId(empId);
        }

        if (name != null && !name.isEmpty() &&
        !Objects.equals(employee.getName(),name)){
            employee.setName(name);
        }

        if (email != null && !email.isEmpty() &&
                !Objects.equals(employee.getEmail(),email)){
            Optional<Employee> employeeOptionalEmail = employeeRepository.findEmployeeByEmail(email);
            if(employeeOptionalEmail.isPresent()){
                throw new IllegalStateException("email taken");
            }
            employee.setEmail(email);
        }

        if (roleName != null && !roleName.isEmpty() &&
                !Objects.equals(employee.getRoleName(),roleName)){
            employee.setRoleName(roleName);
        }

        if (empGrade != null && empGrade>0 && empGrade <=10 &&
        !Objects.equals(employee.getEmpGrade(),empGrade)){
            employee.setEmpGrade(empGrade);
            isEmpGradeUpdated = true;
        }

        if (basicPay != null && basicPay>0 && basicPay < 500000 &&
        !Objects.equals(employee.getBasicPay(),basicPay)){
            employee.setBasicPay(basicPay);
            isBasicPayUpdated = true;
        }
        employeeRepository.save(employee);
        // Update NetPay only if empGrade or basicPay has changed
        if (isEmpGradeUpdated || isBasicPayUpdated) {
            netPayService.calculateHra(employee);
            netPayService.calculateDa(employee);
            netPayService.calculateMa(employee);
            netPayService.calculatePerDayPay(employee);

        }
    }

}
