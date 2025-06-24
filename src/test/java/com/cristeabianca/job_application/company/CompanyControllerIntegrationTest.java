package com.cristeabianca.job_application.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CompanyControllerIntegrationTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private Company sampleCompany;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sampleCompany = new Company();
        sampleCompany.setId(1L);
        sampleCompany.setName("Test Company");
    }

    @Test
    void testGetAllCompanies() {
        when(companyService.getAllCompanies()).thenReturn(List.of(sampleCompany));

        ResponseEntity<List<Company>> response = companyController.getAllCompanies();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetCompanyById() {
        when(companyService.getCompanyById(1L)).thenReturn(sampleCompany);

        ResponseEntity<Company> response = companyController.getCompanyById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleCompany, response.getBody());
    }

    @Test
    void testCreateCompany_Success() {
        when(companyService.createCompany(any())).thenReturn(true);

        ResponseEntity<String> response = companyController.createCompany(sampleCompany);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Company created.", response.getBody());
    }

    @Test
    void testCreateCompany_Failure() {
        when(companyService.createCompany(any())).thenReturn(false);

        ResponseEntity<String> response = companyController.createCompany(sampleCompany);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Company could not be created.", response.getBody());
    }

    @Test
    void testUpdateCompany_Success() {
        when(companyService.updateCompany(anyLong(), any())).thenReturn(true);

        ResponseEntity<String> response = companyController.updateCompany(1L, sampleCompany);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Company updated.", response.getBody());
    }

    @Test
    void testUpdateCompany_Failure() {
        when(companyService.updateCompany(anyLong(), any())).thenReturn(false);

        ResponseEntity<String> response = companyController.updateCompany(1L, sampleCompany);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Company could not be updated.", response.getBody());
    }

    @Test
    void testDeleteCompany_Success() {
        when(companyService.deleteCompanyById(1L)).thenReturn(true);

        ResponseEntity<String> response = companyController.deleteCompanyById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Company deleted.", response.getBody());
    }

    @Test
    void testDeleteCompany_Failure() {
        when(companyService.deleteCompanyById(1L)).thenReturn(false);

        ResponseEntity<String> response = companyController.deleteCompanyById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Company could not be deleted.", response.getBody());
    }
}
