package com.archu.homebudgetmanager.service;

import com.archu.homebudgetmanager.model.Transaction;
import com.archu.homebudgetmanager.repository.ExpenditureRepository;
import com.archu.homebudgetmanager.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceService {
    @Autowired
    ExpenditureRepository expenditureRepository;
    @Autowired
    IncomeRepository incomeRepository;

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        incomeRepository.findAll()
                .forEach(transactions::add);
        expenditureRepository.findAll()
                .forEach(transactions::add);
        return transactions;
    }
}
