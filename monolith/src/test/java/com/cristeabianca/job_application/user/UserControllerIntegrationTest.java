package com.cristeabianca.job_application.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerIntegrationTest {

    private UserService userService;
    private UserController userController;

    @BeforeEach
    void setup() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void testGetAllUsers() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("username"));
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        Page<User> page = new PageImpl<>(List.of(user), pageable, 1);

        when(userService.getAllUsers(any(Pageable.class))).thenReturn(page);

        var response = userController.getAllUsers(0, 10, "username");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals("testuser", response.getBody().getContent().get(0).getUsername());
    }

    @Test
    void testCreateUser_Success() {
        User user = new User();
        user.setUsername("newuser");

        when(userService.createUser(any(User.class))).thenReturn(true);

        var response = userController.createUser(user);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("User created", response.getBody());
    }

    @Test
    void testCreateUser_Failure() {
        User user = new User();
        user.setUsername("baduser");

        when(userService.createUser(any(User.class))).thenReturn(false);

        var response = userController.createUser(user);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Could not create user", response.getBody());
    }

    @Test
    void testGetUserById_Found() {
        User user = new User();
        user.setId(1L);
        user.setUsername("founduser");

        when(userService.getUserById(1L)).thenReturn(user);

        var response = userController.getUserById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("founduser", response.getBody().getUsername());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userService.getUserById(1L)).thenReturn(null);

        var response = userController.getUserById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateUser_Success() {
        User user = new User();
        user.setUsername("updateduser");

        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(true);

        var response = userController.updateUser(1L, user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User updated", response.getBody());
    }

    @Test
    void testUpdateUser_Failure() {
        User user = new User();
        user.setUsername("failupdate");

        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(false);

        var response = userController.updateUser(1L, user);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Could not update user", response.getBody());
    }

    @Test
    void testDeleteUser_Success() {
        when(userService.deleteUser(1L)).thenReturn(true);

        var response = userController.deleteUser(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User deleted", response.getBody());
    }

    @Test
    void testDeleteUser_Failure() {
        when(userService.deleteUser(1L)).thenReturn(false);

        var response = userController.deleteUser(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Could not delete user", response.getBody());
    }
}
