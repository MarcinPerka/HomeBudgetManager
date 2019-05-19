package com.archu.homebudgetmanager.controller;

import com.archu.homebudgetmanager.controller.IncomeController;
import com.archu.homebudgetmanager.model.Income;
import com.archu.homebudgetmanager.model.User;
import com.archu.homebudgetmanager.service.IncomeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure = false)
public class IncomeControllerTest {

    @Mock
    private IncomeService incomeService;

    @InjectMocks
    private IncomeController incomeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private User user;
    private Income income1, income2, income3, income4;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(incomeController).build();
        objectMapper = new ObjectMapper();

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

        income4 = new Income("Some stuff", new BigDecimal(1700), new Date(2019, 10, 11), Income.IncomeCategory.WORK);
        income4.setUser(user);
        ReflectionTestUtils.setField(income4, "id", 4L);
    }

    @Test
    public void testGetIncomeById() throws Exception {
        when(incomeService.getIncomeById(user.getId(), income1.getId())).thenReturn(income1);
        mockMvc.perform(get("/user/{userId}/incomes/{id}", 1, 1)
                .content(objectMapper.writeValueAsString(income1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(income1.getTitle()));
        verify(incomeService).getIncomeById(anyLong(), anyLong());
    }

    @Test
    public void testGetAllIncomes() throws Exception {
        List<Income> incomes = new ArrayList<>(Arrays.asList(income1, income2, income3));

        when(incomeService.getAllIncomes(user.getId())).thenReturn(incomes);
        mockMvc.perform(get("/user/{userId}/incomes", 1)
                .content(objectMapper.writeValueAsString(incomes))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(incomeService).getAllIncomes(anyLong());
    }

    @Test
    public void testGetSumOfIncomes() throws Exception {
        BigDecimal sum = income1.getAmount().add(income2.getAmount());

        when(incomeService.getSumOfIncomes(user.getId())).thenReturn(sum);
        mockMvc.perform(get("/user/{userId}/sumOfIncomes", 1)
                .content(objectMapper.writeValueAsString(sum))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(incomeService).getSumOfIncomes(anyLong());
    }

    @Test
    public void testGetIncomeByMonth() throws Exception {
        List<Income> incomes = new ArrayList<>(Arrays.asList(income1, income3));

        when(incomeService.getIncomesByMonth(user.getId(), 10)).thenReturn(incomes);
        mockMvc.perform(get("/user/{userId}/incomes/month/{month}", 1, 10)
                .content(objectMapper.writeValueAsString(incomes))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(incomeService).getIncomesByMonth(anyLong(), anyInt());
    }

    @Test
    public void testGetSumOfIncomesByCategory() throws Exception {
        List<Income> incomes = new ArrayList<>(Arrays.asList(income1, income2, income3));
        Map<String, BigDecimal> incomesByCategory = new HashMap<>();
        incomesByCategory.put("PARENTS", income1.getAmount());
        incomesByCategory.put("WORK", income2.getAmount().add(income3.getAmount()));

        when(incomeService.getSumOfIncomesByCategory(user.getId())).thenReturn(incomesByCategory);
        mockMvc.perform(get("/user/{userId}/incomes/byCategory", 1)
                .content(objectMapper.writeValueAsString(incomes))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(incomeService).getSumOfIncomesByCategory(anyLong());
    }

    @Test
    public void testGetSumOfIncomesByMonthAndCategory() throws Exception {
        List<Income> incomes = new ArrayList<>(Arrays.asList(income1, income2, income3, income4));
        Map<String, BigDecimal> incomesByCategory = new HashMap<>();
        incomesByCategory.put("PARENTS", income1.getAmount());
        incomesByCategory.put("WORK", income2.getAmount().add(income3.getAmount()));
        incomesByCategory.put("WORK", incomesByCategory.get(income4.getAmount()));

        when(incomeService.getSumOfIncomesByMonthAndCategory(user.getId(), 10)).thenReturn(incomesByCategory);
        mockMvc.perform(get("/user/{userId}/incomes/byCategory/month/{month}", 1, 10)
                .content(objectMapper.writeValueAsString(incomesByCategory))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(incomeService).getSumOfIncomesByMonthAndCategory(anyLong(), anyInt());
    }

    @Test
    public void testGetSumOfIncomesByMonth() throws Exception {
        BigDecimal sum = income1.getAmount().add(income2.getAmount());

        when(incomeService.getSumOfIncomesByMonth(user.getId(), 10)).thenReturn(sum);
        mockMvc.perform(get("/user/{userId}/sumOfIncomes/month/{month}", 1, 10)
                .content(objectMapper.writeValueAsString(sum))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(incomeService).getSumOfIncomesByMonth(anyLong(), anyInt());
    }

    @Test
    public void testAddIncome() throws Exception {
        doNothing().when(incomeService).addIncome(any(Income.class));

        mockMvc.perform(post("/user/{userId}/incomes/", anyLong())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(incomeService).addIncome(any(Income.class));
    }

    @Test
    public void testUpdateIncome() throws Exception {
        doNothing().when(incomeService).updateIncome(any(Income.class), anyLong());

        mockMvc.perform(put("/user/{userId}/incomes/{id}", anyLong(), anyLong())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(incomeService).updateIncome(any(Income.class), anyLong());
    }

    @Test
    public void testDeleteIncome() throws Exception {
        doNothing().when(incomeService).deleteIncomeById(anyLong(), anyLong());

        mockMvc.perform(delete("/user/{userId}/incomes/{id}", anyLong(), anyLong())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(incomeService).deleteIncomeById(anyLong(), anyLong());
    }
}

