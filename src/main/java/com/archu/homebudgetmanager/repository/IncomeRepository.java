package com.archu.homebudgetmanager.repository;

import com.archu.homebudgetmanager.model.Income;
import com.archu.homebudgetmanager.model.IncomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income,Integer> {


    public Optional<Income> findById(Integer id);
    public List<Income> findByDateOfTransaction(Date dateOfTransaction);
    public List<Income> findByIncomeCategory(IncomeCategory incomeCategory);
}
