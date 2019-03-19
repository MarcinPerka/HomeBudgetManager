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
    @Autowired
    ExpenditureRepository expenditureRepository;

    public List<Expenditure> getAllExpenditures() {
        List<Expenditure> expenditures = new ArrayList<>();
        expenditureRepository.findAll()
                .forEach(expenditures::add);
        return expenditures;
    }

    public Expenditure getExpenditureById(Integer id) {
        return expenditureRepository.findById(id).orElse(null);
    }

    public List<Expenditure> getExpendituresByMonth(Integer month) {
        return expenditureRepository.findExpenditureByMonth(month);
    }

    public Map<String, BigDecimal> getSumOfExpendituresByCategory() {
        Map<String, BigDecimal> sumExpenditureByCategory = new HashMap<>();
        List<Expenditure> expenditures = expenditureRepository.findAll();
        expenditures.forEach((expenditure) -> {
            if (sumExpenditureByCategory.containsKey(expenditure.getExpenditureCategory().name())) {
                sumExpenditureByCategory.put(expenditure.getExpenditureCategory().name(), sumExpenditureByCategory.get(expenditure.getExpenditureCategory().name()).add(expenditure.getAmount()));
            } else {
                sumExpenditureByCategory.put(expenditure.getExpenditureCategory().name(), new BigDecimal(String.valueOf(expenditure.getAmount())));
            }

        });
        return sumExpenditureByCategory;
    }

    public BigDecimal getSumOfExpenditures() {
        return expenditureRepository.findSumOfExpenditures();
    }

    public BigDecimal getSumOfExpendituresByMonth(Integer month) {
        return expenditureRepository.findSumOfExpendituresByMonth(month);
    }

    public Map<String, BigDecimal> getSumOfExpendituresByMonthAndCategory(Integer month) {
        return new HashMap<>();// #TODO
    }

    public void addExpenditure(Expenditure expenditure) {
        expenditureRepository.save(expenditure);
    }

    public void deleteExpenditureById(Integer id) {
        expenditureRepository.deleteById(id);
    }

    public void updateExpenditure(Expenditure expenditure, Integer id) {
        Expenditure expenditureToUpdate = expenditureRepository.findById(id).orElse(null);

        if (expenditureToUpdate == null)

            expenditure.setId(id);
        expenditureRepository.save(expenditure);
    }

}
