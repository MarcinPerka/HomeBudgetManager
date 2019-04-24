package com.archu.homebudgetmanager;


import com.archu.homebudgetmanager.model.User;
import com.archu.homebudgetmanager.repository.RoleRepository;
import com.archu.homebudgetmanager.repository.UserRepository;
import com.archu.homebudgetmanager.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    private User user1, user2;

    @Before
    public void setUp() {
        user1 = new User("test", "test", "test@gmail.com");
        user2 = new User("test2", "test", "test2@gmail.com");
        ReflectionTestUtils.setField(user1, "id", 1L);
        ReflectionTestUtils.setField(user1, "id", 2L);
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(user1.getId())).thenReturn(Optional.ofNullable(user1));
        User found = userService.getUserById(user1.getId());
        assertThat(found).isEqualTo(user1);
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserByIdWhenUserNotFound() {
        when(userRepository.findById(user1.getId())).thenReturn(null);
        User found = userService.getUserById(user1.getId());
        assertNull(found);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>(Arrays.asList(user1, user2));

        when(userRepository.findAll()).thenReturn(users);
        List<User> found = userService.getAllUsers();
        assertThat(found).isEqualTo(users);
    }

}
