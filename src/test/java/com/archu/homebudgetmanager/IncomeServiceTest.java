package com.archu.homebudgetmanager;

import com.archu.homebudgetmanager.model.Income;
import com.archu.homebudgetmanager.model.User;
import com.archu.homebudgetmanager.repository.IncomeRepository;
import com.archu.homebudgetmanager.service.IncomeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IncomeServiceTest {

    @InjectMocks
    IncomeService incomeService;

    @Mock
    IncomeRepository incomeRepository;

    private User user;
    private Income income1, income2, income3;

    @Before
    public void setUp() {
        user = new User("test", "test", "test@gmail.com");
        ReflectionTestUtils.setField(user, "id", 1L);
        income1 = new Income("Parents", new BigDecimal(900.39), new Date(2019, 10, 1), Income.IncomeCategory.PARENTS);
        income1.setUser(user);
        ReflectionTestUtils.setField(income1, "id", 1L);

        income2 = new Income("Some stuff", new BigDecimal(1000.39), new Date(2019, 06, 10), Income.IncomeCategory.WORK);
        income2.setTitle("Some stuff");
        income2.setAmount(new BigDecimal(1000.39));
        income2.setDateOfTransaction(new Date(2019, 06, 10));
        income2.setIncomeCategory(Income.IncomeCategory.WORK);
        income2.setUser(user);
        ReflectionTestUtils.setField(income2, "id", 2L);

        income3 = new Income("Some stuff", new BigDecimal(700), new Date(2019, 10, 1), Income.IncomeCategory.WORK);
        income3.setUser(user);
        ReflectionTestUtils.setField(income3, "id", 3L);
    }

    @Test
    public void testGetIncomeById() {
        when(incomeRepository.findByUserIdAndId(user.getId(), income1.getId())).thenReturn(income1);
        Income found = incomeService.getIncomeById(1L, 1L);
        assertThat(found).isEqualTo(income1);
    }

    @Test
    public void testGetAllIncomes() {
        List<Income> incomes = new ArrayList<>(Arrays.asList(income1, income2));

        when(incomeRepository.findByUserId(user.getId())).thenReturn(incomes);
        List<Income> found = incomeService.getAllIncomes(1L);
        assertThat(found).isEqualTo(incomes);
    }

    @Test
    public void testGetSumOfIncomesByCategory() {
        List<Income> incomes = new ArrayList<>(Arrays.asList(income1, income2, income3));
        Map<String, BigDecimal> incomeByCategory = new HashMap<>();
        incomeByCategory.put("PARENTS", income1.getAmount());
        incomeByCategory.put("WORK", income2.getAmount().add(income3.getAmount()));

        when(incomeRepository.findByUserId(user.getId())).thenReturn(incomes);
        Map<String, BigDecimal> found = incomeService.getSumOfIncomesByCategory(1L);
        assertThat(found).isEqualTo(incomeByCategory);
    }

    @Test
    public void testGetSumOfIncomesByMonthAndCategory() {
        List<Income> incomes = new ArrayList<>(Arrays.asList(income1, income3));
        Map<String, BigDecimal> incomesByCategory = new HashMap<>();
        incomesByCategory.put("PARENTS", income1.getAmount());
        incomesByCategory.put("WORK", income3.getAmount());

        when(incomeRepository.findIncomeByUserIdAndMonth(user.getId(), 10)).thenReturn(incomes);
        Map<String, BigDecimal> found = incomeService.getSumOfIncomesByMonthAndCategory(1L, 10);
        assertThat(found).isEqualTo(incomesByCategory);
    }

    @Test
    public void testGetSumOfIncomes() {
        List<Income> incomes = new ArrayList<>(Arrays.asList(income1, income2));
        BigDecimal sum = income1.getAmount().add(income2.getAmount());

        when(incomeRepository.findSumOfIncomesByUserId(user.getId())).thenReturn(sum);
        BigDecimal found = incomeService.getSumOfIncomes(1L);
        assertThat(found).isEqualTo(sum);
    }

    @Test
    public void testGetSumOfIncomesByMonth() {
        List<Income> incomes = new ArrayList<>(Arrays.asList(income1, income2));
        BigDecimal sum = income1.getAmount();

        when(incomeRepository.findSumOfIncomesByMonth(user.getId(), 10)).thenReturn(sum);
        BigDecimal found = incomeService.getSumOfIncomesByMonth(1L, 10);
        assertThat(found).isEqualTo(sum);
    }
}
