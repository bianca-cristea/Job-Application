package com.cristeabianca.job_application.user;

import com.cristeabianca.job_application.user.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
    void testGetAllUsersWithPagination() {
        List<User> users = List.of(new User(), new User());
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(users, pageable, users.size());

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<User> result = userService.getAllUsers(pageable);

        assertEquals(2, result.getContent().size());
        verify(userRepository, times(1)).findAll(pageable);
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
