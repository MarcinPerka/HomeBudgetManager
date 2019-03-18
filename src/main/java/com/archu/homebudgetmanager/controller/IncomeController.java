package com.archu.homebudgetmanager.controller;


import com.archu.homebudgetmanager.model.Expenditure;
import com.archu.homebudgetmanager.model.Income;
import com.archu.homebudgetmanager.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IncomeController {
    @Autowired
    IncomeService incomeService;

    @GetMapping("incomes")
    public List<Income> getAllIncomes(){
        return incomeService.getAllIncomes();
    }

    @GetMapping("incomes/{id}")
    public Income getIncomeById(@PathVariable Integer id){
        return incomeService.getIncomeById(id);
    }

    @DeleteMapping("incomes/{id}")
    public void deleteIncomeById(@PathVariable Integer id){
        incomeService.deleteIncomeById(id);
    }

    @PostMapping("income/")
    public void addIncome(Income income){
        incomeService.addIncome(income);
    }

    @PutMapping("income/{id}")
    public void updateIncome(Income income, @PathVariable Integer id){
        incomeService.updateIncome(income, id);
    }

}
