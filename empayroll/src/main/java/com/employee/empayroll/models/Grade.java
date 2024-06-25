package com.employee.empayroll.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "grades")
public class Grade {
    @Id
    public Integer gradeId;
    public Integer hraPercentage;
    public Integer daPercentage;
    public Integer maPercentage;

    public Grade() {
    }

    public Grade(Integer gradeId,
                 Integer hraPercentage,
                 Integer daPercentage,
                 Integer maPercentage) {
        this.gradeId = gradeId;
        this.hraPercentage = hraPercentage;
        this.daPercentage = daPercentage;
        this.maPercentage = maPercentage;
    }

}
