package com.archu.homebudgetmanager.controller;

import com.archu.homebudgetmanager.model.Expenditure;
import com.archu.homebudgetmanager.service.ExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class ExpenditureController {
    @Autowired
    ExpenditureService expenditureService;

    @GetMapping("expenditures")
    public List<Expenditure> getAllExpenditures() {
        return expenditureService.getAllExpenditures();
    }

    @GetMapping("expenditures/{id}")
    public Expenditure getExpenditureById(@PathVariable Integer id) {
        return expenditureService.getExpenditureById(id);
    }

    @GetMapping("expenditures/byCategory")
    public Map<String, BigDecimal> getSumOfExpendituresByCategory() {
        return expenditureService.getSumOfExpendituresByCategory();
    }

    @GetMapping("expenditures/byCategory/month/{month}")
    public Map<String, BigDecimal> getSumOfIncomeByMonthAndCategory(@PathVariable Integer month){
        return expenditureService.getSumOfExpendituresByMonthAndCategory(month);
    }

    @GetMapping("expenditures/month/{month}")
    public List<Expenditure> getExpendituresByMonth(@PathVariable Integer month) {
        return expenditureService.getExpendituresByMonth(month);
    }

    @GetMapping("expendituresSum")
    public BigDecimal getSumOfExpenditures() {
        return expenditureService.getSumOfExpenditures();
    }

    @GetMapping("expendituresSum/month/{month}")
    public BigDecimal getSumOfExpendituresByMonth(@PathVariable Integer month) {
        return expenditureService.getSumOfExpendituresByMonth(month);
    }

    @DeleteMapping("expenditures/{id}")
    public void deleteExpenditureById(@PathVariable Integer id) {
        expenditureService.deleteExpenditureById(id);
    }

    @PostMapping("expenditures/")
    public void addExpenditure(Expenditure expenditure) {
        expenditureService.addExpenditure(expenditure);
    }

    @PutMapping("expenditures/{id}")
    public void updateExpenditure(Expenditure expenditure, @PathVariable Integer id) {
        expenditureService.updateExpenditure(expenditure, id);
    }
}
