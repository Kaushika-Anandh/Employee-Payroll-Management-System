package com.employee.empayroll.service;

import com.employee.empayroll.models.Employee;
import com.employee.empayroll.models.Grade;
import com.employee.empayroll.repository.EmployeeRepository;
import com.employee.empayroll.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GradeService {

    public final GradeRepository gradeRepository;
    public final EmployeeRepository employeeRepository;;
    public final NetPayService netPayService;

    @Autowired
    public GradeService(GradeRepository gradeRepository, EmployeeRepository employeeRepository,  NetPayService netPayService) {
        this.gradeRepository = gradeRepository;
        this.employeeRepository = employeeRepository;
        this.netPayService = netPayService;
    }

    //viewing grade table contents
    public List<Grade> getGrades() {
        return gradeRepository.findAll();
    }

    //editing grade table
    public void updateGrade(Integer gradeId,
                            Integer hraPercentage,
                            Integer daPercentage,
                            Integer maPercentage) {
        //validating existence of grade number
        Grade grade = gradeRepository.findGradeByGradeId(gradeId)
                .orElseThrow(() -> new IllegalStateException("grade number "+gradeId+" does not exist"));

        boolean hraUpdated = false;
        boolean daUpdated = false;
        boolean maUpdated = false;

        //changing hra
        if (hraPercentage != null && hraPercentage > 0 && hraPercentage<= 100 &&
                !Objects.equals(grade.getHraPercentage(), hraPercentage)){
            grade.setHraPercentage(hraPercentage);
            hraUpdated = true;
        }

        if (daPercentage != null && daPercentage > 0 && daPercentage <= 100 &&
                !Objects.equals(grade.getDaPercentage(), daPercentage)){
            grade.setDaPercentage(daPercentage);
            daUpdated = true;
        }

        if (maPercentage != null && maPercentage > 0 && maPercentage <= 100 &&
                !Objects.equals(grade.getMaPercentage(), maPercentage)){
            grade.setMaPercentage(maPercentage);
            maUpdated = true;
        }


        if (hraUpdated || daUpdated || maUpdated) {
            gradeRepository.save(grade);
            //Fetch all employees with updated grade
            List<Employee> employees = employeeRepository.findAllByEmpGrade(gradeId);
            //update NetPay for each employee based on specific updates
            for (Employee employee : employees) {
                if (hraUpdated) netPayService.calculateHra(employee);
                if (daUpdated) netPayService.calculateDa(employee);
                if (maUpdated) netPayService.calculateMa(employee);
                netPayService.calculatePerDayPay(employee);
            }
        }
        gradeRepository.save(grade);
    }



}
