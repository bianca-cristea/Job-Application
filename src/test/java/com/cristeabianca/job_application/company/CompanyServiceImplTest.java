package com.cristeabianca.job_application.company;

import com.cristeabianca.job_application.company.impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private Company company;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        company = new Company();
        company.setId(1L);
        company.setName("Test Company");
        company.setDescription("Description");
        company.setJobs(Collections.emptyList());
    }

    @Test
    void getAllCompanies_shouldReturnList() {
        when(companyRepository.findAll()).thenReturn(List.of(company));

        List<Company> companies = companyService.getAllCompanies();

        assertEquals(1, companies.size());
        verify(companyRepository).findAll();
    }

    @Test
    void getCompanyById_whenExists_shouldReturnCompany() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        Company found = companyService.getCompanyById(1L);

        assertNotNull(found);
        assertEquals("Test Company", found.getName());
    }

    @Test
    void getCompanyById_whenNotExists_shouldReturnNull() {
        when(companyRepository.findById(2L)).thenReturn(Optional.empty());

        Company found = companyService.getCompanyById(2L);

        assertNull(found);
    }

    @Test
    void createCompany_shouldReturnTrueOnSuccess() {
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        boolean created = companyService.createCompany(company);

        assertTrue(created);
        verify(companyRepository).save(company);
    }

    @Test
    void createCompany_shouldReturnFalseOnException() {
        when(companyRepository.save(any(Company.class))).thenThrow(new RuntimeException());

        boolean created = companyService.createCompany(company);

        assertFalse(created);
    }

    @Test
    void updateCompany_whenExists_shouldUpdateAndReturnTrue() {
        Company updatedCompany = new Company();
        updatedCompany.setName("Updated Name");
        updatedCompany.setDescription("Updated Desc");
        updatedCompany.setJobs(Collections.emptyList());

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        boolean updated = companyService.updateCompany(1L, updatedCompany);

        assertTrue(updated);
        assertEquals("Updated Name", company.getName());
        assertEquals("Updated Desc", company.getDescription());
        verify(companyRepository).save(company);
    }

    @Test
    void updateCompany_whenNotExists_shouldReturnFalse() {
        when(companyRepository.findById(2L)).thenReturn(Optional.empty());

        boolean updated = companyService.updateCompany(2L, company);

        assertFalse(updated);
        verify(companyRepository, never()).save(any());
    }

    @Test
    void deleteCompanyById_shouldReturnTrueOnSuccess() {
        doNothing().when(companyRepository).deleteById(1L);

        boolean deleted = companyService.deleteCompanyById(1L);

        assertTrue(deleted);
        verify(companyRepository).deleteById(1L);
    }

    @Test
    void deleteCompanyById_shouldReturnFalseOnException() {
        doThrow(new RuntimeException()).when(companyRepository).deleteById(1L);

        boolean deleted = companyService.deleteCompanyById(1L);

        assertFalse(deleted);
    }
}
