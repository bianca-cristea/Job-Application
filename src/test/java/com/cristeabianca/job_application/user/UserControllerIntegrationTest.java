package com.cristeabianca.job_application.user;

import com.cristeabianca.job_application.role.Role;
import com.cristeabianca.job_application.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private String jwtToken;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("admin");
        user.setPassword("encodedpassword"); // poate fi orice, nu se validează în test
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        jwtToken = TestUtil.generateTestToken(user);
    }

    @Test
    void testGetAllUsers_withJwt_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateUser_withJwt_shouldCreateSuccessfully() throws Exception {
        String newUserJson = "{\"username\":\"testuser\",\"password\":\"pass123\"}";

        mockMvc.perform(post("/users")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testUnauthorizedAccess_shouldFail() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isUnauthorized());
    }
}
