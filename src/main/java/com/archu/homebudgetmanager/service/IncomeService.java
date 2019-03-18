package com.archu.homebudgetmanager.service;

import com.archu.homebudgetmanager.model.Expenditure;
import com.archu.homebudgetmanager.model.Income;
import com.archu.homebudgetmanager.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeService {
    @Autowired
    IncomeRepository incomeRepository;

    public List<Income> getAllIncomes(){
        List<Income>incomes = new ArrayList<>();
        incomeRepository.findAll().forEach(incomes:: add);
        return incomes;
    }

    public Income getIncomeById(Integer id){
        return incomeRepository.findById(id).orElse(null);
    }

    public void addIncome(Income income){
        incomeRepository.save(income);
    }

    public void deleteIncomeById(Integer id){
        incomeRepository.deleteById(id);
    }

    public void updateIncome(Income income, Integer id) {
        Income incomeToUpdate = incomeRepository.findById(id).orElse(null);

        if (incomeToUpdate == null)

            income.setId(id);
        incomeRepository.save(income);
    }
}
