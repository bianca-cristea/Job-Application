package com.cristeabianca.job_application.user;

import com.cristeabianca.job_application.user.User;
import com.cristeabianca.job_application.user.UserRepository;
import com.cristeabianca.job_application.user.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUserSuccess() {
        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("password123");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        boolean result = userService.createUser(user);

        assertTrue(result);
        verify(userRepository).save(user);
    }

    @Test
    void testCreateUserNull() {
        boolean result = userService.createUser(null);
        assertFalse(result);
    }

    @Test
    void testCreateUserEmptyPassword() {
        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("");

        boolean result = userService.createUser(user);
        assertFalse(result);
    }

    @Test
    void testUpdateUserSuccess() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("john_doe");
        existingUser.setPassword("oldEncodedPassword");

        User updatedUser = new User();
        updatedUser.setUsername("john_doe_updated");
        updatedUser.setPassword("newPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(updatedUser.getPassword(), existingUser.getPassword())).thenReturn(false);
        when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn("encodedNewPassword");
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        boolean result = userService.updateUser(userId, updatedUser);

        assertTrue(result);
        verify(userRepository).save(existingUser);
        assertEquals("encodedNewPassword", existingUser.getPassword());
    }

    @Test
    void testUpdateUserNotFound() {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setUsername("john_doe_updated");
        updatedUser.setPassword("newPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        boolean result = userService.updateUser(userId, updatedUser);

        assertFalse(result);
    }

    @Test
    void testDeleteUserSuccess() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        boolean result = userService.deleteUser(userId);

        assertTrue(result);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void testDeleteUserFailure() {
        Long userId = 1L;

        doThrow(new RuntimeException("Database error")).when(userRepository).deleteById(userId);

        boolean result = userService.deleteUser(userId);

        assertFalse(result);
        verify(userRepository).deleteById(userId);
    }
}
