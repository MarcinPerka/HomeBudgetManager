package com.archu.homebudgetmanager;

import com.archu.homebudgetmanager.model.User;
import com.archu.homebudgetmanager.repository.UserRepository;
import com.archu.homebudgetmanager.service.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testLoadUserByUsername_activeUser() {
        User user = new User("username", "password", "email@gmail.com");
        when(this.userRepository.findByUsername(any(String.class))).thenReturn(user);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername("username");
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsername_userNotFound() {
        when(this.userRepository.findByUsername(any(String.class))).thenReturn(null);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername("username");
        assertNull(userDetails);
    }
}
