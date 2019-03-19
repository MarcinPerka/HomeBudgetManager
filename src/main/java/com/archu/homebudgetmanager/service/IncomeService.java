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

    public List<Income> getAllIncomes() {
        List<Income> incomes = new ArrayList<>();
        incomeRepository.findAll()
                .forEach(incomes::add);
        return incomes;
    }

    public Income getIncomeById(Integer id) {
        return incomeRepository.findById(id).orElse(null);
    }

    public List<Income> getIncomesByMonth(Integer month) {
        return incomeRepository.findIncomeByMonth(month);
    }

    public Map<String, BigDecimal> getSumOfIncomesByCategory() {
        Map<String, BigDecimal> sumIncomeByCategory = new HashMap<>();
        List<Income> incomes = incomeRepository.findAll();
        incomes.forEach((income) -> {
            if (sumIncomeByCategory.containsKey(income.getIncomeCategory().name())) {
                sumIncomeByCategory.put(income.getIncomeCategory().name(), sumIncomeByCategory.get(income.getIncomeCategory().name()).add(income.getAmount()));
            } else {
                sumIncomeByCategory.put(income.getIncomeCategory().name(), new BigDecimal(String.valueOf(income.getAmount())));
            }

        });
        return sumIncomeByCategory;
    }

    public BigDecimal getSumOfIncomes() {
        return incomeRepository.findSumOfIncomes();
    }

    public BigDecimal getSumOfIncomesByMonth(Integer month) {
        return incomeRepository.findSumOfIncomesByMonth(month);
    }

    public Map<String, BigDecimal> getSumOfIncomesByMonthAndCategory(Integer month) {
        Map<String, BigDecimal> sumIncomeByMonthAndCategory = new HashMap<>();
        List<Income> incomes = getIncomesByMonth(month);
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

    public void deleteIncomeById(Integer id) {
        incomeRepository.deleteById(id);
    }

    public void updateIncome(Income income, Integer id) {
        Income incomeToUpdate = incomeRepository.findById(id).orElse(null);

        if (incomeToUpdate == null)

            income.setId(id);
        incomeRepository.save(income);
    }
}
