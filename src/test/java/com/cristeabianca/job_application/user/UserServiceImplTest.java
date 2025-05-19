package com.cristeabianca.job_application.user;

import com.cristeabianca.job_application.user.User;
import com.cristeabianca.job_application.user.UserRepository;
import com.cristeabianca.job_application.user.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserByIdFound() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User result = userService.getUserById(1L);
        assertNull(result);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        boolean created = userService.createUser(user);
        assertTrue(created);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserExists() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("oldUsername");

        User updateUser = new User();
        updateUser.setUsername("newUsername");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        boolean updated = userService.updateUser(1L, updateUser);
        assertTrue(updated);
        assertEquals("newUsername", existingUser.getUsername());
    }

    @Test
    void testUpdateUserNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean updated = userService.updateUser(1L, new User());
        assertFalse(updated);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        boolean deleted = userService.deleteUser(1L);
        assertTrue(deleted);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
