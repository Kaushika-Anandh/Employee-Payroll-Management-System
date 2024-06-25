package com.employee.empayroll.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "netpay")
public class NetPay {

    @Id
    @Column(name = "emp_id", nullable = false)
    private String empId;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "emp_id", referencedColumnName = "empId")
    private Employee employee;

    private Double hra;
    private Double da;
    private Double ma;
    private Double perDayPay;

    public NetPay( Employee employee,
                   Double hra,
                   Double da,
                   Double ma,
                   Double perDayPay) {
        this.empId = employee.getEmpId();
        this.employee = employee;
        this.hra = hra;
        this.da = da;
        this.ma = ma;
        this.perDayPay = perDayPay;
    }
}
