package com.archu.homebudgetmanager.service;

import com.archu.homebudgetmanager.model.Expenditure;
import com.archu.homebudgetmanager.repository.ExpenditureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenditureService {

    private  final ExpenditureRepository expenditureRepository;

    @Autowired
    public ExpenditureService(ExpenditureRepository expenditureRepository) {
        this.expenditureRepository = expenditureRepository;
    }

    public List<Expenditure> getAllExpenditures(Long userId) {
        List<Expenditure> expenditures = new ArrayList<>();
        expenditureRepository.findByUserId(userId)
                .forEach(expenditures::add);
        return expenditures;
    }

    public Expenditure getExpenditureById(Long userId, Long id) {
        return expenditureRepository.findByUserIdAndId(userId, id);
    }

    public List<Expenditure> getExpendituresByMonth(Long userId, Integer month) {
        return expenditureRepository.findByUserIdAndMonth(userId, month);
    }

    public Map<String, BigDecimal> getSumOfExpendituresByCategory(Long userId) {
        Map<String, BigDecimal> sumExpenditureByCategory = new HashMap<>();
        List<Expenditure> expenditures = expenditureRepository.findByUserId(userId);
        expenditures.forEach((expenditure) -> {
            if (sumExpenditureByCategory.containsKey(expenditure.getExpenditureCategory().name())) {
                sumExpenditureByCategory.put(expenditure.getExpenditureCategory().name(), sumExpenditureByCategory.get(expenditure.getExpenditureCategory().name()).add(expenditure.getAmount()));
            } else {
                sumExpenditureByCategory.put(expenditure.getExpenditureCategory().name(), new BigDecimal(String.valueOf(expenditure.getAmount())));
            }

        });
        return sumExpenditureByCategory;
    }

    public BigDecimal getSumOfExpenditures(Long userId) {
        return expenditureRepository.findSumOfExpendituresByUserId(userId);
    }

    public BigDecimal getSumOfExpendituresByMonth(Long userId, Integer month) {
        return expenditureRepository.findSumOfExpendituresByUserIdAndMonth(userId, month);
    }

    public Map<String, BigDecimal> getSumOfExpendituresByMonthAndCategory(Long userId, Integer month) {
        Map<String, BigDecimal> sumExpendituresByMonthAndCategory = new HashMap<>();
        List<Expenditure> expenditures = getExpendituresByMonth(userId, month);
        expenditures.forEach((income) -> {
            if (sumExpendituresByMonthAndCategory.containsKey(income.getExpenditureCategory().name())) {
                sumExpendituresByMonthAndCategory.put(income.getExpenditureCategory().name(), sumExpendituresByMonthAndCategory.get(income.getAmount()));
            } else {
                sumExpendituresByMonthAndCategory.put(income.getExpenditureCategory().name(), new BigDecimal(String.valueOf(income.getAmount())));
            }

        });
        return sumExpendituresByMonthAndCategory;
    }

    public void addExpenditure(Expenditure expenditure) {
        expenditureRepository.save(expenditure);
    }

    public void deleteExpenditureById(Long id) {
        expenditureRepository.deleteById(id);
    }

    public void updateExpenditure(Expenditure expenditure, Long id) {
        Expenditure expenditureToUpdate = expenditureRepository.findById(id).orElse(null);

        if (expenditureToUpdate == null)

            expenditure.setId(id);
        expenditureRepository.save(expenditure);
    }
}
