package com.archu.homebudgetmanager.controller;

import com.archu.homebudgetmanager.model.Transaction;
import com.archu.homebudgetmanager.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalanceController {
    @Autowired
    BalanceService balanceService;

    @GetMapping("/balance")
    public List<Transaction> getAllTransactions() {
        return balanceService.getAllTransactions();
    }

}
