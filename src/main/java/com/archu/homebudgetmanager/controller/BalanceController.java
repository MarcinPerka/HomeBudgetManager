package com.archu.homebudgetmanager.controller;

import com.archu.homebudgetmanager.model.Transaction;
import com.archu.homebudgetmanager.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/user/{userId}")
@PreAuthorize("(hasRole('ROLE_USER') AND #userId == authentication.principal.id) OR hasRole('ROLE_ADMIN')")
public class BalanceController {
    @Autowired
    BalanceService balanceService;

    @GetMapping("/balance")
    public List<Transaction> getAllTransactions(@PathVariable Long userId) {
        return balanceService.getAllTransactions(userId);
    }

    @GetMapping("/balance/month/{month}")
    public BigDecimal getBalanceByMonth(@PathVariable Long userId, @PathVariable Integer month) {
        return balanceService.getBalanceByMonth(userId, month);
    }

    @GetMapping("balance/totalBalance")
    public BigDecimal getTotalBalance(@PathVariable Long userId) {
        return balanceService.getTotalBalance(userId);
    }
}
