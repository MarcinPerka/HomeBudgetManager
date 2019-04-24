package com.archu.homebudgetmanager.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "expenditure")
public class Expenditure extends Transaction {

    private static final long serialVersionUID = 2L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "expenditure_category", columnDefinition = "ENUM('FOOD', 'TRANSPORT', 'PAYMENTS', 'ENTERTAINMENT','HOLIDAYS', 'UNCATEGORIZED')")
    private ExpenditureCategory expenditureCategory;

    public enum ExpenditureCategory {FOOD, TRANSPORT, PAYMENTS, ENTERTAINMENT, HOLIDAYS, UNCATEGORIZED;}

    public Expenditure() {
    }

    public Expenditure(String title, BigDecimal amount, Date dateOfTransaction, ExpenditureCategory expenditureCategory) {
        this.title = title;
        this.amount = amount;
        this.dateOfTransaction = dateOfTransaction;
        this.expenditureCategory = expenditureCategory;
    }


    public ExpenditureCategory getExpenditureCategory() {
        return expenditureCategory;
    }

    public void setExpenditureCategory(ExpenditureCategory expenditureCategory) {
        this.expenditureCategory = expenditureCategory;
    }
}


