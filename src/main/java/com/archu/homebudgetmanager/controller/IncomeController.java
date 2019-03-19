package com.archu.homebudgetmanager.controller;

import com.archu.homebudgetmanager.model.Income;
import com.archu.homebudgetmanager.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class IncomeController {
    @Autowired
    IncomeService incomeService;

    @GetMapping("incomes")
    public List<Income> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @GetMapping("incomes/{id}")
    public Income getIncomeById(@PathVariable Integer id) {
        return incomeService.getIncomeById(id);
    }

    @GetMapping("incomes/byCategory")
    public Map<String, BigDecimal> getSumOfIncomesByCategory() {
        return incomeService.getSumOfIncomesByCategory();
    }

    @GetMapping("incomes/byCategory/month/{month}")
    public Map<String, BigDecimal> getSumOfIncomeByMonthAndCategory(@PathVariable Integer month) {
        return incomeService.getSumOfIncomesByMonthAndCategory(month);
    }

    @GetMapping("incomes/month/{month}")
    public List<Income> getIncomesByMonth(@PathVariable Integer month) {
        return incomeService.getIncomesByMonth(month);
    }

    @GetMapping("incomesSum")
    public BigDecimal getSumOfIncomes() {
        return incomeService.getSumOfIncomes();
    }

    @GetMapping("incomesSum/month/{month}")
    public BigDecimal getSumOfIncomesByMonth(@PathVariable Integer month) {
        return incomeService.getSumOfIncomesByMonth(month);
    }

    @DeleteMapping("incomes/{id}")

    public void deleteIncomeById(@PathVariable Integer id) {
        incomeService.deleteIncomeById(id);
    }

    @PostMapping("income/")
    public void addIncome(Income income) {
        incomeService.addIncome(income);
    }

    @PutMapping("income/{id}")
    public void updateIncome(Income income, @PathVariable Integer id) {
        incomeService.updateIncome(income, id);
    }

}
