package com.cristeabianca.job_application.interview;

import com.cristeabianca.job_application.application.Application;
import com.cristeabianca.job_application.application.ApplicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InterviewControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Application application;

    @BeforeEach
    void setup() {
        interviewRepository.deleteAll();
        applicationRepository.deleteAll();

        application = new Application();
        applicationRepository.save(application);
    }

    @Test
    void createInterview_thenGetByApplication() throws Exception {
        Interview interview = new Interview();
        interview.setLocation("Office");
        interview.setScheduledAt(LocalDateTime.now().plusDays(2));

        // Create interview
        mockMvc.perform(post("/interviews/application/{applicationId}", application.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(interview)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Interview created")));

        // Get interview by application
        mockMvc.perform(get("/interviews/application/{applicationId}", application.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value("Office"));
    }

    @Test
    void getByApplication_whenNotExists_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/interviews/application/{applicationId}", 9999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateInterview_whenExists_shouldReturnOk() throws Exception {
        Interview interview = new Interview();
        interview.setLocation("Old Location");
        interview.setScheduledAt(LocalDateTime.now().plusDays(1));
        interview.setApplication(application);
        interview = interviewRepository.save(interview);

        Interview updated = new Interview();
        updated.setLocation("New Location");
        updated.setScheduledAt(LocalDateTime.now().plusDays(3));

        mockMvc.perform(put("/interviews/{id}", interview.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(content().string("Interview updated"));

        // Check updated value
        mockMvc.perform(get("/interviews/application/{applicationId}", application.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value("New Location"));
    }

    @Test
    void updateInterview_whenNotExists_shouldReturnNotFound() throws Exception {
        Interview updated = new Interview();
        updated.setLocation("New Location");
        updated.setScheduledAt(LocalDateTime.now());

        mockMvc.perform(put("/interviews/{id}", 9999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Interview not found"));
    }

    @Test
    void deleteInterview_whenExists_shouldReturnOk() throws Exception {
        Interview interview = new Interview();
        interview.setLocation("Location");
        interview.setScheduledAt(LocalDateTime.now());
        interview.setApplication(application);
        interview = interviewRepository.save(interview);

        mockMvc.perform(delete("/interviews/{id}", interview.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }

    @Test
    void deleteInterview_whenNotExists_shouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/interviews/{id}", 9999L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Not found"));
    }
}
