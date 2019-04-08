package com.archu.homebudgetmanager.controller;

import com.archu.homebudgetmanager.exception.UserAlreadyExistAuthenticationException;
import com.archu.homebudgetmanager.model.User;
import com.archu.homebudgetmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("(hasRole('ROLE_USER') AND #id == authentication.principal.id) OR hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/registration")
    public void registerUser(User user) throws UserAlreadyExistAuthenticationException {
        userService.createUser(user);
    }

    @PreAuthorize("(hasRole('ROLE_USER') AND #id == authentication.principal.id) OR hasRole('ROLE_ADMIN')")
    @PutMapping("user/{id}")
    public void updateUser(User user, @PathVariable Long id) throws Exception {
        userService.updateUser(user, id);
    }

    @PreAuthorize("(hasRole('ROLE_USER') AND #id == authentication.principal.id) OR hasRole('ROLE_ADMIN')")
    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
