package com.archu.homebudgetmanager.repository;

import com.archu.homebudgetmanager.model.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Integer> {

    public Optional<Expenditure> findById(Integer id);

    @Query("SELECT e FROM Expenditure e WHERE FUNCTION('MONTH',e.dateOfTransaction) = ?1")
    public List<Expenditure> findExpenditureByMonth(Integer month);

    @Query("SELECT SUM(e.amount) FROM Expenditure e")
    public BigDecimal findSumOfExpenditures();

    @Query("SELECT COALESCE(SUM(e.amount),0) FROM Expenditure e WHERE FUNCTION('MONTH', e.dateOfTransaction) =?1")
    public BigDecimal findSumOfExpendituresByMonth(Integer month);
}
