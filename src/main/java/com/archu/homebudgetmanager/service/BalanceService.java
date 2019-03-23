package com.archu.homebudgetmanager.service;

import com.archu.homebudgetmanager.model.Transaction;
import com.archu.homebudgetmanager.repository.ExpenditureRepository;
import com.archu.homebudgetmanager.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public BigDecimal getBalanceByMonth(Integer month) {
        return incomeRepository.findSumOfIncomesByMonth(month)
                .subtract(expenditureRepository.findSumOfExpendituresByMonth(month));
    }
}
