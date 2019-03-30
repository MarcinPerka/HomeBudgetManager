package com.archu.homebudgetmanager.controller;

import com.archu.homebudgetmanager.model.Income;
import com.archu.homebudgetmanager.model.User;
import com.archu.homebudgetmanager.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/{userId}")
public class IncomeController {
    @Autowired
    IncomeService incomeService;

    @GetMapping("/incomes")
    public List<Income> getAllIncomes(@PathVariable Long userId) {
        return incomeService.getAllIncomes(userId);
    }

    @GetMapping("/incomes/{id}")
    public Income getIncomeById(@PathVariable Long userId, @PathVariable Long id) {
        return incomeService.getIncomeById(userId, id);
    }

    @GetMapping("/incomes/byCategory")
    public Map<String, BigDecimal> getSumOfIncomesByCategory(@PathVariable Long userId) {
        return incomeService.getSumOfIncomesByCategory(userId);
    }

    @GetMapping("/incomes/byCategory/month/{month}")
    public Map<String, BigDecimal> getSumOfIncomeByMonthAndCategory(@PathVariable Long userId, @PathVariable Integer month) {
        return incomeService.getSumOfIncomesByMonthAndCategory(userId, month);
    }

    @GetMapping("/incomes/month/{month}")
    public List<Income> getIncomesByMonth(@PathVariable Long userId, @PathVariable Integer month) {
        return incomeService.getIncomesByMonth(userId, month);
    }

    @GetMapping("/sumOfIncomes")
    public BigDecimal getSumOfIncomes(@PathVariable Long userId) {
        return incomeService.getSumOfIncomes(userId);
    }

    @GetMapping("/sumOfIncomes/month/{month}")
    public BigDecimal getSumOfIncomesByMonth(@PathVariable Long userId, @PathVariable Integer month) {
        return incomeService.getSumOfIncomesByMonth(userId, month);
    }

    @DeleteMapping("/incomes/{id}")

    public void deleteIncomeById(@PathVariable Long id) {
        incomeService.deleteIncomeById(id);
    }

    @PostMapping("/incomes/")
    public void addIncome(@PathVariable Long userId, Income income) {
        income.setUser(new User(userId));
        incomeService.addIncome(income);
    }

    @PutMapping("/incomes/{id}")
    public void updateIncome(@PathVariable Long userId, Income income, @PathVariable Long id) {
        income.setUser(new User(userId));
        incomeService.updateIncome(income, id);
    }

}
