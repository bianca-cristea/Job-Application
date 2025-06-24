package com.cristeabianca.job_application.company;

import com.cristeabianca.job_application.company.Company;
import com.cristeabianca.job_application.company.CompanyRepository;
import com.cristeabianca.job_application.company.CompanyService;
import com.cristeabianca.job_application.company.impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyServiceImplTest {

    @Mock private CompanyRepository companyRepository;
    @InjectMocks private CompanyServiceImpl companyService;

    private Company company;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        company = new Company();
        company.setId(1L);
        company.setName("SoftCorp");
        company.setDescription("Enterprise Solutions");
    }

    @Test
    void shouldReturnAllCompanies() {
        when(companyRepository.findAll()).thenReturn(List.of(company));

        List<Company> companies = companyService.getAllCompanies();

        assertEquals(1, companies.size());
        assertEquals("SoftCorp", companies.get(0).getName());
    }

    @Test
    void shouldReturnCompanyById() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        Company found = companyService.getCompanyById(1L);

        assertNotNull(found);
        assertEquals("SoftCorp", found.getName());
    }

    @Test
    void shouldReturnNullIfCompanyNotFound() {
        when(companyRepository.findById(999L)).thenReturn(Optional.empty());

        Company result = companyService.getCompanyById(999L);

        assertNull(result);
    }

    @Test
    void shouldCreateCompanySuccessfully() {
        when(companyRepository.save(company)).thenReturn(company);

        boolean result = companyService.createCompany(company);

        assertTrue(result);
        verify(companyRepository).save(company);
    }

    @Test
    void shouldFailToCreateCompanyOnException() {
        when(companyRepository.save(any())).thenThrow(RuntimeException.class);

        boolean result = companyService.createCompany(company);

        assertFalse(result);
    }

    @Test
    void shouldUpdateCompanyIfExists() {
        Company updated = new Company();
        updated.setName("Updated Name");
        updated.setDescription("Updated Description");

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(companyRepository.save(any())).thenReturn(company);

        boolean result = companyService.updateCompany(1L, updated);

        assertTrue(result);
        assertEquals("Updated Name", company.getName());
        assertEquals("Updated Description", company.getDescription());
    }

    @Test
    void shouldNotUpdateCompanyIfNotFound() {
        when(companyRepository.findById(999L)).thenReturn(Optional.empty());

        boolean result = companyService.updateCompany(999L, company);

        assertFalse(result);
    }

    @Test
    void shouldDeleteCompanySuccessfully() {
        doNothing().when(companyRepository).deleteById(1L);

        boolean result = companyService.deleteCompanyById(1L);

        assertTrue(result);
    }

    @Test
    void shouldFailToDeleteOnException() {
        doThrow(RuntimeException.class).when(companyRepository).deleteById(1L);

        boolean result = companyService.deleteCompanyById(1L);

        assertFalse(result);
    }
}
