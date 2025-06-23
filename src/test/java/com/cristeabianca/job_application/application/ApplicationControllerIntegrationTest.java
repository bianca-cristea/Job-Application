package com.cristeabianca.job_application.application;

import com.cristeabianca.job_application.job.Job;
import com.cristeabianca.job_application.job.JobRepository;
import com.cristeabianca.job_application.user.User;
import com.cristeabianca.job_application.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private Job job;

    @BeforeEach
    void setup() {
        applicationRepository.deleteAll();
        userRepository.deleteAll();
        jobRepository.deleteAll();

        user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user = userRepository.save(user);

        job = new Job();
        job.setTitle("Test Job");
        job = jobRepository.save(job);
    }

    @Test
    void createApplication_thenGetApplicationById() throws Exception {
        Application app = new Application();
        app.setStatus("Pending");

        // POST to create application
        mockMvc.perform(post("/applications/user/{userId}/job/{jobId}", user.getId(), job.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(app)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Application created")));

        // Verify application was created
        Application savedApp = applicationRepository.findAll().get(0);

        // GET application by ID
        mockMvc.perform(get("/applications/{id}", savedApp.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedApp.getId()))
                .andExpect(jsonPath("$.status").value("Pending"))
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.job.id").value(job.getId()));
    }

    @Test
    void getAllApplications_shouldReturnEmptyInitially() throws Exception {
        mockMvc.perform(get("/applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getApplicationsByUser_shouldReturnApplications() throws Exception {
        // Create one application directly in DB
        Application app = new Application();
        app.setStatus("Pending");
        app.setUser(user);
        app.setJob(job);
        applicationRepository.save(app);

        mockMvc.perform(get("/applications/user/{userId}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].user.id").value(user.getId()));
    }

    @Test
    void updateApplication_shouldChangeStatus() throws Exception {
        Application app = new Application();
        app.setStatus("Pending");
        app.setUser(user);
        app.setJob(job);
        app = applicationRepository.save(app);

        app.setStatus("Accepted");

        mockMvc.perform(put("/applications/{id}", app.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(app)))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated"));

        // Verify update in DB
        Application updated = applicationRepository.findById(app.getId()).orElseThrow();
        assert(updated.getStatus().equals("Accepted"));
    }

    @Test
    void deleteApplication_shouldRemoveApplication() throws Exception {
        Application app = new Application();
        app.setStatus("Pending");
        app.setUser(user);
        app.setJob(job);
        app = applicationRepository.save(app);

        mockMvc.perform(delete("/applications/{id}", app.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

        assert(applicationRepository.findById(app.getId()).isEmpty());
    }

    @Test
    void createApplication_withInvalidUser_shouldReturnBadRequest() throws Exception {
        Application app = new Application();
        app.setStatus("Pending");

        Long invalidUserId = 999L;

        mockMvc.perform(post("/applications/user/{userId}/job/{jobId}", invalidUserId, job.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(app)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Failed to create application")));
    }
}
