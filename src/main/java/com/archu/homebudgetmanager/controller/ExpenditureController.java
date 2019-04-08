package com.archu.homebudgetmanager.controller;

import com.archu.homebudgetmanager.model.Expenditure;
import com.archu.homebudgetmanager.model.User;
import com.archu.homebudgetmanager.service.ExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/{userId}")
@PreAuthorize("(hasRole('ROLE_USER') AND #userId == authentication.principal.id) OR hasRole('ROLE_ADMIN')")
public class ExpenditureController {

    private final ExpenditureService expenditureService;

    @Autowired
    public ExpenditureController(ExpenditureService expenditureService) {
        this.expenditureService = expenditureService;
    }

    @GetMapping("/expenditures")
    public List<Expenditure> getAllExpenditures(@PathVariable Long userId) {
        return expenditureService.getAllExpenditures(userId);
    }

    @GetMapping("/expenditures/{id}")
    public Expenditure getExpenditureById(@PathVariable Long userId, @PathVariable Long id) {
        return expenditureService.getExpenditureById(userId, id);
    }

    @GetMapping("/expenditures/byCategory")
    public Map<String, BigDecimal> getSumOfExpendituresByCategory(@PathVariable Long userId) {
        return expenditureService.getSumOfExpendituresByCategory(userId);
    }

    @GetMapping("/expenditures/byCategory/month/{month}")
    public Map<String, BigDecimal> getSumOfExpenditureByMonthAndCategory(@PathVariable Long userId, @PathVariable Integer month) {
        return expenditureService.getSumOfExpendituresByMonthAndCategory(userId, month);
    }

    @GetMapping("/expenditures/month/{month}")
    public List<Expenditure> getExpendituresByMonth(@PathVariable Long userId, @PathVariable Integer month) {
        return expenditureService.getExpendituresByMonth(userId, month);
    }

    @GetMapping("/sumOfExpenditures")
    public BigDecimal getSumOfExpenditures(@PathVariable Long userId) {
        return expenditureService.getSumOfExpenditures(userId);
    }

    @GetMapping("/sumOfExpenditures/month/{month}")
    public BigDecimal getSumOfExpendituresByMonth(@PathVariable Long userId, @PathVariable Integer month) {
        return expenditureService.getSumOfExpendituresByMonth(userId, month);
    }

    @DeleteMapping("/expenditures/{id}")
    public void deleteExpenditureById(@PathVariable Long id) {
        expenditureService.deleteExpenditureById(id);
    }

    @PostMapping("/expenditures/")
    public void addExpenditure(@PathVariable Long userId, Expenditure expenditure) {
        expenditure.setUser(new User(userId));
        expenditureService.addExpenditure(expenditure);
    }

    @PutMapping("/expenditures/{id}")
    public void updateExpenditure(@PathVariable Long userId, Expenditure expenditure, @PathVariable Long id) {
        expenditure.setUser(new User(userId));
        expenditureService.updateExpenditure(expenditure, id);
    }
}
