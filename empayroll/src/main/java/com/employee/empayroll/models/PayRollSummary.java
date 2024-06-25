package com.employee.empayroll.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "pay_roll_summary")
public class PayRollSummary implements Serializable {
    @EmbeddedId
    private SummaryId id;
    Double monthlyPay;

    public PayRollSummary() {
    }

    public PayRollSummary(SummaryId id, Double monthlyPay) {
        this.id = id;
        this.monthlyPay = monthlyPay;
    }
}
