package com.archu.homebudgetmanager.repository;

import com.archu.homebudgetmanager.model.Expenditure;
import com.archu.homebudgetmanager.model.ExpenditureCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Integer> {

    public Optional<Expenditure> findById(Integer id);
    public List<Expenditure> findByDateOfTransaction(Date dateOfTransaction);
    public List<Expenditure> findByExpenditureCategory(ExpenditureCategory expenditureCategory);


}
