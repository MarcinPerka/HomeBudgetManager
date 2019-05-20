package com.archu.homebudgetmanager;

import com.archu.homebudgetmanager.controller.UserController;
import com.archu.homebudgetmanager.exception.UserAlreadyExistAuthenticationException;
import com.archu.homebudgetmanager.model.User;
import com.archu.homebudgetmanager.service.UserService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure = false)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private User user1, user2;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();

        user1 = new User("test", "test", "test@gmail.com");
        user2 = new User("test2", "test", "test2@gmail.com");
        ReflectionTestUtils.setField(user1, "id", 1L);
        ReflectionTestUtils.setField(user1, "id", 2L);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user1, user2));

        when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(get("/users")
                .content(objectMapper.writeValueAsString(users))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService).getAllUsers();
    }

    @Test
    public void testGetUser() throws Exception {

        when(userService.getUserById(user1.getId())).thenReturn(user1);
        mockMvc.perform(get("/user/{userId}", 1)
                .content(objectMapper.writeValueAsString(user1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        when(userService.getUserById(user2.getId())).thenReturn(user2);
        mockMvc.perform(get("/user/{userId}", 2)
                .content(objectMapper.writeValueAsString(user2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(2)).getUserById(anyLong());
    }

    @Test
    public void testRegisterUser() throws Exception {
        doAnswer((i) -> {
            System.out.println("Created");
            return null;
        }).when(userService).createUser(any(User.class));
        mockMvc.perform(post("/registration")
                .content(objectMapper.writeValueAsString(any(User.class)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService).createUser(any(User.class));
    }

    @Test(expected = UserAlreadyExistAuthenticationException.class)
    public void testRegisterUser_userFound() throws Exception {
        doThrow(new UserAlreadyExistAuthenticationException("User exists.")).when(userService).createUser(any(User.class));

        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService).createUser(any(User.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
        doAnswer((i) -> {
            System.out.println("Updated");
            return null;
        }).when(userService).updateUser(any(User.class), anyLong());
        mockMvc.perform(put("/user/{userId}", eq(anyLong()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService).updateUser(any(User.class), anyLong());
    }

    @Test
    public void testDeleteUser() throws Exception {
        doAnswer((i) -> {
            System.out.println("Deleted");
            return null;
        }).when(userService).deleteUserById(anyLong());
        mockMvc.perform(delete("/user/{userId}", anyLong())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService).deleteUserById(anyLong());
    }

}
