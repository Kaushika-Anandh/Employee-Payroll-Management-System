package com.employee.empayroll.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "salary")
public class Salary implements Serializable {

    @EmbeddedId
    private SalaryId id;

    @JsonIgnore
    @ManyToOne
    @MapsId("empId")
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

    private  Integer workingDays;
    private Double monthlyPay;

    public Salary() {
    }

    public Salary(Employee employee,
                  String month,
                  Integer year,
                  Integer workingDays) {
        this.id = new SalaryId(employee.getEmpId(), month, year);
        this.employee = employee;
        this.workingDays = workingDays;
    }

}
