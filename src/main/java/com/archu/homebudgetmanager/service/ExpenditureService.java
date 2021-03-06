package com.archu.homebudgetmanager.service;

import com.archu.homebudgetmanager.model.Expenditure;
import com.archu.homebudgetmanager.repository.ExpenditureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenditureService {

    private final ExpenditureRepository expenditureRepository;

    @Autowired
    public ExpenditureService(ExpenditureRepository expenditureRepository) {
        this.expenditureRepository = expenditureRepository;
    }

    @PreAuthorize("#userId == authentication.principal.id OR hasRole('ADMIN')")
    public List<Expenditure> getAllExpenditures(Long userId) {
        List<Expenditure> expenditures = new ArrayList<>();
        expenditures.addAll(expenditureRepository.findByUserId(userId));
        return expenditures;
    }

    @PreAuthorize("#userId == authentication.principal.id OR hasRole('ADMIN')")
    public Expenditure getExpenditureById(Long userId, Long id) {
        return expenditureRepository.findByUserIdAndId(userId, id);
    }

    @PreAuthorize("#userId == authentication.principal.id OR hasRole('ADMIN')")
    public List<Expenditure> getExpendituresByMonth(Long userId, Integer month) {
        return expenditureRepository.findByUserIdAndMonth(userId, month);
    }

    @PreAuthorize("#userId == authentication.principal.id OR hasRole('ADMIN')")
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

    @PreAuthorize("#userId == authentication.principal.id OR hasRole('ADMIN')")
    public BigDecimal getSumOfExpenditures(Long userId) {
        return expenditureRepository.findSumOfExpendituresByUserId(userId);
    }

    @PreAuthorize("#userId == authentication.principal.id OR hasRole('ADMIN')")
    public BigDecimal getSumOfExpendituresByMonth(Long userId, Integer month) {
        return expenditureRepository.findSumOfExpendituresByUserIdAndMonth(userId, month);
    }

    @PreAuthorize("#userId == authentication.principal.id OR hasRole('ADMIN')")
    public Map<String, BigDecimal> getSumOfExpendituresByMonthAndCategory(Long userId, Integer month) {
        Map<String, BigDecimal> sumExpendituresByMonthAndCategory = new HashMap<>();
        List<Expenditure> expenditures = getExpendituresByMonth(userId, month);
        expenditures.forEach((expenditure) -> {
            if (sumExpendituresByMonthAndCategory.containsKey(expenditure.getExpenditureCategory().name())) {
                sumExpendituresByMonthAndCategory.put(expenditure.getExpenditureCategory().name(), sumExpendituresByMonthAndCategory.get(expenditure.getAmount()));
            } else {
                sumExpendituresByMonthAndCategory.put(expenditure.getExpenditureCategory().name(), new BigDecimal(String.valueOf(expenditure.getAmount())));
            }

        });
        return sumExpendituresByMonthAndCategory;
    }

    @PreAuthorize("#expenditure.user.id == authentication.principal.id OR hasRole('ADMIN')")
    public void addExpenditure(Expenditure expenditure) {
        expenditureRepository.save(expenditure);
    }

    @PreAuthorize("#userId == authentication.principal.id OR hasRole('ADMIN')")
    public void deleteExpenditureById(Long userId, Long id) {
        expenditureRepository.deleteById(id);
    }

    @PreAuthorize("#expenditure.user.id == authentication.principal.id OR hasRole('ADMIN')")
    public void updateExpenditure(Expenditure expenditure, Long id) {
        Expenditure expenditureToUpdate = expenditureRepository.findById(id).orElse(null);

        if (expenditureToUpdate != null) {
            expenditureToUpdate.setTitle(expenditure.getTitle());
            expenditureToUpdate.setAmount(expenditure.getAmount());
            expenditureToUpdate.setDateOfTransaction(expenditure.getDateOfTransaction());
            expenditureToUpdate.setExpenditureCategory(expenditure.getExpenditureCategory());
            expenditureRepository.save(expenditure);
        }
    }
}
