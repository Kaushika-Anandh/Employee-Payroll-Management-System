package com.employee.empayroll.models;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SalaryId implements Serializable {

    private String empId;
    private String month;
    private Integer year;

    public SalaryId() {}

    public SalaryId(String empId, String month, Integer year) {
        this.empId = empId;
        this.month = month;
        this.year = year;
    }

    // Getters and setters

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaryId salaryId = (SalaryId) o;
        return empId.equals(salaryId.empId) &&
                month.equals(salaryId.month) &&
                year.equals(salaryId.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, month, year);
    }



}
