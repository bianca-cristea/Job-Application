package com.cristeabianca.job_application.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleControllerIntegrationTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    private Role role;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        role = new Role(1L, "ADMIN");
    }

    @Test
    void getAllRoles_ReturnsList() {
        when(roleService.getAllRoles()).thenReturn(List.of(role));

        ResponseEntity<List<Role>> response = roleController.getAllRoles();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(roleService, times(1)).getAllRoles();
    }

    @Test
    void getRoleById_Found() {
        when(roleService.getRoleById(1L)).thenReturn(role);

        ResponseEntity<Role> response = roleController.getRoleById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(role, response.getBody());
    }

    @Test
    void getRoleById_NotFound() {
        when(roleService.getRoleById(1L)).thenReturn(null);

        ResponseEntity<Role> response = roleController.getRoleById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void createRole_Success() {
        when(roleService.createRole(role)).thenReturn(true);

        ResponseEntity<String> response = roleController.createRole(role);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Role created", response.getBody());
    }

    @Test
    void createRole_Fail() {
        when(roleService.createRole(role)).thenReturn(false);

        ResponseEntity<String> response = roleController.createRole(role);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Could not create role", response.getBody());
    }

    @Test
    void updateRole_Success() {
        when(roleService.updateRole(1L)).thenReturn(true);

        ResponseEntity<String> response = roleController.updateRole(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Role updated", response.getBody());
    }

    @Test
    void updateRole_Fail() {
        when(roleService.updateRole(1L)).thenReturn(false);

        ResponseEntity<String> response = roleController.updateRole(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Could not update role", response.getBody());
    }

    @Test
    void deleteRole_Success() {
        when(roleService.deleteRole(1L)).thenReturn(true);

        ResponseEntity<String> response = roleController.deleteRole(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Role deleted", response.getBody());
    }

    @Test
    void deleteRole_Fail() {
        when(roleService.deleteRole(1L)).thenReturn(false);

        ResponseEntity<String> response = roleController.deleteRole(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Could not delete role", response.getBody());
    }
}
