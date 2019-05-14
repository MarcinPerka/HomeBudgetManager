package com.archu.homebudgetmanager.service;


import com.archu.homebudgetmanager.exception.UserAlreadyExistAuthenticationException;
import com.archu.homebudgetmanager.model.Role;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user1, user2;

    @Before
    public void setUp() {
        user1 = new User();
        user1.setUsername("test");
        user1.setPassword("test");
        user1.setEmail("test@gmail.com");
        user1.setEnabled(true);
        user1.setExpired(false);
        user1.setCredentialsExpired(false);
        user1.setLocked(false);
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setCode("ROLE_USER");
        role.setLabel("User");
        roles.add(role);
        user1.setRoles(roles);
        user2 = new User("test2", "test", "test2@gmail.com");
        ReflectionTestUtils.setField(user1, "id", 1L);
        ReflectionTestUtils.setField(user2, "id", 2L);
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

    @Test
    public void testCreateUser() throws UserAlreadyExistAuthenticationException {
        when(userRepository.save(any(User.class))).thenReturn(user1);
        userService.createUser(user1);
    }

    @Test
    public void testDeleteUserById() {
        doNothing().when(userRepository).delete(any(User.class));
        userService.deleteUserById(user1.getId());
    }

    @Test
    public void testUpdateUser() throws Exception {
        User updatedUser = new User("admin", "admin", "admin@tmqi.com");
        ReflectionTestUtils.setField(updatedUser, "id", 1L);
        when(userRepository.findById(user1.getId())).thenReturn(Optional.ofNullable(user1));
        when(userRepository.save(user1)).thenReturn(updatedUser);
        userService.updateUser(updatedUser, user1.getId());

        assertEquals(updatedUser.getId(),user1.getId());
    }

}
