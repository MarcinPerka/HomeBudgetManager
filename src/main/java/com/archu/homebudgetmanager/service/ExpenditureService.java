package com.archu.homebudgetmanager.service;

import com.archu.homebudgetmanager.model.Expenditure;
import com.archu.homebudgetmanager.repository.ExpenditureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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

    public List<Expenditure> getExpenditureByMonth(Integer month){
        List<Expenditure> expenditures = new ArrayList<>();
        List<Expenditure> expendituresByMonth = new ArrayList<>();
        expenditureRepository.findAll().forEach(expenditures::add);
        expenditures.forEach((expenditure -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(expenditure.getDateOfTransaction());
            int monthExpenditure = calendar.get(Calendar.MONTH);
            if(monthExpenditure == month)
                expendituresByMonth.add(expenditure);

        }));
        return expendituresByMonth;
    }
    public Map<String, BigDecimal> getSumOfExpendituresByCategory() {
        Map<String, BigDecimal> sumExpenditureByCategory = new HashMap<>();
        List<Expenditure> expenditures = new ArrayList<>();
        expenditureRepository.findAll().forEach(expenditures::add);
        expenditures.forEach((expenditure) -> {
            if (sumExpenditureByCategory.containsKey(expenditure.getExpenditureCategory().name())) {
                sumExpenditureByCategory.put(expenditure.getExpenditureCategory().name(), sumExpenditureByCategory.get(expenditure.getExpenditureCategory().name()).add(expenditure.getAmount()));
            } else {
                sumExpenditureByCategory.put(expenditure.getExpenditureCategory().name(), new BigDecimal(String.valueOf(expenditure.getAmount())));
            }

        });
        return sumExpenditureByCategory;

    }
}
