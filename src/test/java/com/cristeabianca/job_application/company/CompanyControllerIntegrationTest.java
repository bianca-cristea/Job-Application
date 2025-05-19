package com.cristeabianca.job_application.company;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        companyRepository.deleteAll();
    }

    @Test
    void createCompany_thenGetCompanyById() throws Exception {
        Company company = new Company();
        company.setName("New Company");
        company.setDescription("Company description");

        // Create company
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(company)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Company created")));

        Company savedCompany = companyRepository.findAll().get(0);

        // Get company by id
        mockMvc.perform(get("/companies/{id}", savedCompany.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedCompany.getId()))
                .andExpect(jsonPath("$.name").value("New Company"))
                .andExpect(jsonPath("$.description").value("Company description"));
    }

    @Test
    void getAllCompanies_shouldReturnEmptyListInitially() throws Exception {
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void updateCompany_shouldChangeNameAndDescription() throws Exception {
        Company company = new Company();
        company.setName("Old Name");
        company.setDescription("Old Desc");
        company = companyRepository.save(company);

        company.setName("Updated Name");
        company.setDescription("Updated Desc");

        mockMvc.perform(put("/companies/{id}", company.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(company)))
                .andExpect(status().isOk())
                .andExpect(content().string("Company updated."));

        Company updated = companyRepository.findById(company.getId()).orElseThrow();
        assert(updated.getName().equals("Updated Name"));
        assert(updated.getDescription().equals("Updated Desc"));
    }

    @Test
    void deleteCompany_shouldRemoveCompany() throws Exception {
        Company company = new Company();
        company.setName("To Be Deleted");
        company = companyRepository.save(company);

        mockMvc.perform(delete("/companies/{id}", company.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Company deleted."));

        assert(companyRepository.findById(company.getId()).isEmpty());
    }

    @Test
    void getCompanyById_whenNotExists_shouldReturnOkWithNullBody() throws Exception {
        mockMvc.perform(get("/companies/{id}", 999L))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
