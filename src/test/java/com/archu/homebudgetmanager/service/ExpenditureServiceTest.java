package com.archu.homebudgetmanager.service;

import com.archu.homebudgetmanager.model.Expenditure;
import com.archu.homebudgetmanager.model.User;
import com.archu.homebudgetmanager.repository.ExpenditureRepository;
import com.archu.homebudgetmanager.service.ExpenditureService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpenditureServiceTest {

    @InjectMocks
    private ExpenditureService expenditureService;

    @Mock
    private ExpenditureRepository expenditureRepository;

    private User user;
    private Expenditure expenditure1, expenditure2, expenditure3;

    @Before
    public void setUp() {
        user = new User("test", "test", "test@gmail.com");
        ReflectionTestUtils.setField(user, "id", 1L);

        expenditure1 = new Expenditure();
        expenditure1.setTitle("Food");
        expenditure1.setAmount(new BigDecimal(-100));
        expenditure1.setDateOfTransaction(new Date(2019, 10, 10));
        expenditure1.setExpenditureCategory(Expenditure.ExpenditureCategory.FOOD);
        expenditure1.setUser(user);
        ReflectionTestUtils.setField(expenditure1, "id", 1L);

        expenditure2 = new Expenditure("Stuff", new BigDecimal(-10.12), new Date(2019, 11, 10), Expenditure.ExpenditureCategory.UNCATEGORIZED);
        expenditure2.setUser(user);
        ReflectionTestUtils.setField(expenditure2, "id", 2L);

        expenditure3 = new Expenditure();
        expenditure3.setTitle("Stuff");
        expenditure3.setAmount(new BigDecimal(-10.12));
        expenditure3.setDateOfTransaction(new Date(2019, 10, 15));
        expenditure3.setExpenditureCategory(Expenditure.ExpenditureCategory.UNCATEGORIZED);
        expenditure3.setUser(user);
        ReflectionTestUtils.setField(expenditure3, "id", 3L);
    }

    @Test
    public void testGetExpenditureById() {
        when(expenditureRepository.findByUserIdAndId(user.getId(), expenditure1.getId())).thenReturn(expenditure1);
        Expenditure found = expenditureService.getExpenditureById(1L, 1L);
        assertThat(found).isEqualTo(expenditure1);
    }

    @Test
    public void testGetAllExpenditures() {
        List<Expenditure> expenditures = new ArrayList<>(Arrays.asList(expenditure1, expenditure2));

        when(expenditureRepository.findByUserId(user.getId())).thenReturn(expenditures);
        List<Expenditure> found = expenditureService.getAllExpenditures(1L);
        assertThat(found).isEqualTo(expenditures);
    }

    @Test
    public void testGetSumOfExpendituresByCategory() {
        List<Expenditure> expenditures = new ArrayList<>(Arrays.asList(expenditure1, expenditure2, expenditure3));
        Map<String, BigDecimal> expendituresByCategory = new HashMap<>();
        expendituresByCategory.put("FOOD", new BigDecimal(-100));
        expendituresByCategory.put("UNCATEGORIZED", expenditure2.getAmount().add(expenditure3.getAmount()));

        when(expenditureRepository.findByUserId(user.getId())).thenReturn(expenditures);
        Map<String, BigDecimal> found = expenditureService.getSumOfExpendituresByCategory(1L);
        assertThat(found).isEqualTo(expendituresByCategory);
    }

    @Test
    public void testGetSumOfExpendituresByMonthAndCategory() {
        List<Expenditure> expenditures = new ArrayList<>(Arrays.asList(expenditure1, expenditure3));
        Map<String, BigDecimal> expendituresByCategory = new HashMap<>();
        expendituresByCategory.put("FOOD", expenditure1.getAmount());
        expendituresByCategory.put("UNCATEGORIZED", expenditure3.getAmount());
        when(expenditureRepository.findByUserIdAndMonth(user.getId(), 10)).thenReturn(expenditures);
        Map<String, BigDecimal> found = expenditureService.getSumOfExpendituresByMonthAndCategory(1L, 10);
        assertThat(found).isEqualTo(expendituresByCategory);
    }

    @Test
    public void testGetSumOfExpenditures() {
        BigDecimal sum = expenditure1.getAmount().add(expenditure2.getAmount());

        when(expenditureRepository.findSumOfExpendituresByUserId(user.getId())).thenReturn(sum);
        BigDecimal found = expenditureService.getSumOfExpenditures(1L);
        assertThat(found).isEqualTo(sum);
    }

    @Test
    public void testGetSumOfExpendituresByMonth() {
        List<Expenditure> expenditures = new ArrayList<>();
        expenditures.add(expenditure1);
        expenditures.add(expenditure2);
        BigDecimal sum = expenditure1.getAmount();

        when(expenditureRepository.findSumOfExpendituresByUserIdAndMonth(user.getId(), 10)).thenReturn(sum);
        BigDecimal found = expenditureService.getSumOfExpendituresByMonth(1L, 10);
        assertThat(found).isEqualTo(sum);
    }

    @Test
    public void testAddExpenditure() {
        when(expenditureRepository.save(any(Expenditure.class))).thenReturn(expenditure1);
        expenditureService.addExpenditure(expenditure1);
    }

    @Test
    public void testDeleteExpenditureById() {
        doNothing().when(expenditureRepository).delete(any(Expenditure.class));
        expenditureService.deleteExpenditureById(user.getId(),expenditure1.getId());
    }

    @Test
    public void testUpdateExpenditure() {
        Expenditure updatedExpenditure = new Expenditure("Venezia", new BigDecimal(-100.03),new Date(2019,3,3), Expenditure.ExpenditureCategory.HOLIDAYS);
        ReflectionTestUtils.setField(updatedExpenditure, "id", 1L);
        when(expenditureRepository.findById(expenditure1.getId())).thenReturn(Optional.ofNullable(expenditure1));
        when(expenditureRepository.save(expenditure1)).thenReturn(updatedExpenditure);
        expenditureService.updateExpenditure(updatedExpenditure,user.getId());
    }
}
