package com.employee.empayroll.models;

import java.io.Serializable;

public class SummaryId implements Serializable {
    private String empId;
    private String name;
    private String roleName;
    private String month;
    private Integer year;

    public SummaryId() {

    }

    public SummaryId(String empId, String name, String roleName, String month, Integer year) {
        this.empId = empId;
        this.name = name;
        this.roleName = roleName;
        this.month = month;
        this.year = year;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
}
