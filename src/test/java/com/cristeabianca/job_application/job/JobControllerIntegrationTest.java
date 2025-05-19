package com.cristeabianca.job_application.job;

import com.cristeabianca.job_application.company.Company;
import com.cristeabianca.job_application.company.CompanyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JobControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Company testCompany;

    @BeforeEach
    void setUp() {
        jobRepository.deleteAll();
        companyRepository.deleteAll();

        testCompany = new Company();
        testCompany.setName("TestCompany");
        testCompany.setDescription("IT company");
        testCompany = companyRepository.save(testCompany);
    }

    @Test
    void testCreateAndGetJob() throws Exception {
        Job job = new Job();
        job.setTitle("Backend Developer");
        job.setDescription("Spring Boot job");
        job.setLocation("Remote");
        job.setMinSalary("3000");
        job.setMaxSalary("5000");
        job.setCompany(testCompany);

        String jobJson = objectMapper.writeValueAsString(job);

        mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jobJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/jobs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Backend Developer"));
    }
}
