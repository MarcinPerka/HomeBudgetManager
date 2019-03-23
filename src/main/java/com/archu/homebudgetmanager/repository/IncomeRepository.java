package com.archu.homebudgetmanager.repository;

import com.archu.homebudgetmanager.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Integer> {


    public Optional<Income> findById(Integer id);

    @Query("SELECT i FROM Income i WHERE FUNCTION('MONTH',i.dateOfTransaction) = ?1")
    public List<Income> findIncomeByMonth(Integer month);

    @Query("SELECT SUM(i.amount) FROM Income i")
    public BigDecimal findSumOfIncomes();

    @Query("SELECT COALESCE(SUM(i.amount),0) FROM Income i WHERE FUNCTION('MONTH', i.dateOfTransaction) =?1")
    public BigDecimal findSumOfIncomesByMonth(Integer month);
}
