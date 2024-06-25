package com.employee.empayroll.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table (name = "employee")
public class Employee {

    @Id
    @Column(name = "emp_id", nullable = false)
    private String empId;


    private String name;
    private String email;
    public String roleName;
    public Integer empGrade;
    public Double basicPay;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Salary> salaries;

    @JsonIgnore
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private NetPay netPay;

    public Employee(String empId,
                    String name,
                    String email,
                    String roleName,
                    Integer empGrade,
                    Double basicPay) {
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.roleName = roleName;
        this.empGrade = empGrade;
        this.basicPay = basicPay;
    }


    public Employee() {

    }
}
