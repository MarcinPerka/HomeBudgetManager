package com.archu.homebudgetmanager.service;

import com.archu.homebudgetmanager.model.Transaction;
import com.archu.homebudgetmanager.repository.ExpenditureRepository;
import com.archu.homebudgetmanager.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceService {
    @Autowired
    ExpenditureRepository expenditureRepository;
    @Autowired
    IncomeRepository incomeRepository;

    public List<Transaction> getAllTransactions(Long userId) {
        List<Transaction> transactions = new ArrayList<>();
        incomeRepository.findByUserId(userId)
                .forEach(transactions::add);
        expenditureRepository.findByUserId(userId)
                .forEach(transactions::add);
        return transactions;
    }

    public BigDecimal getBalanceByMonth(Long userId, Integer month) {
        return incomeRepository.findSumOfIncomesByMonth(userId, month)
                .subtract(expenditureRepository.findSumOfExpendituresByUserIdAndMonth(userId, month));
    }

    public BigDecimal getTotalBalance(Long userId) {
        return incomeRepository.findSumOfIncomesByUserId(userId).subtract(expenditureRepository.findSumOfExpendituresByUserId(userId));
    }
}
