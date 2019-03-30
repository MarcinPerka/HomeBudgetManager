package com.archu.homebudgetmanager.service;

import com.archu.homebudgetmanager.model.Income;
import com.archu.homebudgetmanager.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IncomeService {
    @Autowired
    IncomeRepository incomeRepository;

    public List<Income> getAllIncomes(Long userId) {
        List<Income> incomes = new ArrayList<>();
        incomeRepository.findByUserId(userId)
                .forEach(incomes::add);
        return incomes;
    }

    public Income getIncomeById(Long userId, Long id) {
        return incomeRepository.findByUserIdAndId(userId, id);
    }

    public List<Income> getIncomesByMonth(Long userId, Integer month) {
        return incomeRepository.findIncomeByUserIdAndMonth(userId, month);
    }

    public Map<String, BigDecimal> getSumOfIncomesByCategory(Long userId) {
        Map<String, BigDecimal> sumIncomeByCategory = new HashMap<>();
        List<Income> incomes = incomeRepository.findByUserId(userId);
        incomes.forEach((income) -> {
            if (sumIncomeByCategory.containsKey(income.getIncomeCategory().name())) {
                sumIncomeByCategory.put(income.getIncomeCategory().name(), sumIncomeByCategory.get(income.getIncomeCategory().name()).add(income.getAmount()));
            } else {
                sumIncomeByCategory.put(income.getIncomeCategory().name(), new BigDecimal(String.valueOf(income.getAmount())));
            }

        });
        return sumIncomeByCategory;
    }

    public BigDecimal getSumOfIncomes(Long userId) {
        return incomeRepository.findSumOfIncomesByUserId(userId);
    }

    public BigDecimal getSumOfIncomesByMonth(Long userId, Integer month) {
        return incomeRepository.findSumOfIncomesByMonth(userId, month);
    }

    public Map<String, BigDecimal> getSumOfIncomesByMonthAndCategory(Long userId, Integer month) {
        Map<String, BigDecimal> sumIncomeByMonthAndCategory = new HashMap<>();
        List<Income> incomes = getIncomesByMonth(userId, month);
        incomes.forEach((income) -> {
            if (sumIncomeByMonthAndCategory.containsKey(income.getIncomeCategory().name())) {
                sumIncomeByMonthAndCategory.put(income.getIncomeCategory().name(), sumIncomeByMonthAndCategory.get(income.getAmount()));
            } else {
                sumIncomeByMonthAndCategory.put(income.getIncomeCategory().name(), new BigDecimal(String.valueOf(income.getAmount())));
            }
        });
        return sumIncomeByMonthAndCategory;
    }

    public void addIncome(Income income) {
        incomeRepository.save(income);
    }

    public void deleteIncomeById(Long id) {
        incomeRepository.deleteById(id);
    }

    public void updateIncome(Income income, Long id) {
        Income incomeToUpdate = incomeRepository.findById(id).orElse(null);

        if (incomeToUpdate == null)

            income.setId(id);
        incomeRepository.save(income);
    }
}
