package com.cristeabianca.job_application.role;

import com.cristeabianca.job_application.role.Role;
import com.cristeabianca.job_application.role.RoleRepository;
import com.cristeabianca.job_application.role.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleServiceImpl roleService;

    private Role role;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        roleService = new RoleServiceImpl(roleRepository);

        role = new Role(1L, "ADMIN");
    }

    @Test
    void getAllRoles_ReturnsList() {
        when(roleRepository.findAll()).thenReturn(List.of(role));

        List<Role> roles = roleService.getAllRoles();

        assertNotNull(roles);
        assertEquals(1, roles.size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void getRoleById_Found() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Role result = roleService.getRoleById(1L);

        assertNotNull(result);
        assertEquals(role, result);
    }

    @Test
    void getRoleById_NotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        Role result = roleService.getRoleById(1L);

        assertNull(result);
    }

    @Test
    void createRole_Success() {
        when(roleRepository.save(role)).thenReturn(role);

        boolean result = roleService.createRole(role);

        assertTrue(result);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void createRole_Failure() {
        when(roleRepository.save(role)).thenThrow(new RuntimeException());

        boolean result = roleService.createRole(role);

        assertFalse(result);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void updateRole_Success() {
        when(roleRepository.existsById(1L)).thenReturn(true);
        doNothing().when(roleRepository).deleteById(1L);

        boolean result = roleService.updateRole(1L);

        assertTrue(result);
        verify(roleRepository, times(1)).existsById(1L);
        verify(roleRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateRole_Fail() {
        when(roleRepository.existsById(1L)).thenReturn(false);

        boolean result = roleService.updateRole(1L);

        assertFalse(result);
        verify(roleRepository, times(1)).existsById(1L);
        verify(roleRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteRole_Success() {
        when(roleRepository.existsById(1L)).thenReturn(true);
        doNothing().when(roleRepository).deleteById(1L);

        boolean result = roleService.deleteRole(1L);

        assertTrue(result);
        verify(roleRepository, times(1)).existsById(1L);
        verify(roleRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteRole_Fail() {
        when(roleRepository.existsById(1L)).thenReturn(false);

        boolean result = roleService.deleteRole(1L);

        assertFalse(result);
        verify(roleRepository, times(1)).existsById(1L);
        verify(roleRepository, never()).deleteById(anyLong());
    }
}
