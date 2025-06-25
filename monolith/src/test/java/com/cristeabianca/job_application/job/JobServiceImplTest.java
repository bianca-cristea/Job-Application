package com.cristeabianca.job_application.job;

import com.cristeabianca.job_application.application.Application;
import com.cristeabianca.job_application.application.ApplicationRepository;
import com.cristeabianca.job_application.company.Company;
import com.cristeabianca.job_application.company.CompanyRepository;
import com.cristeabianca.job_application.job.Job;
import com.cristeabianca.job_application.job.JobRepository;
import com.cristeabianca.job_application.job.impl.JobServiceImpl;
import com.cristeabianca.job_application.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobServiceImplTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private JobServiceImpl jobService;

    private Job job;
    private Company company;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        company = new Company();
        company.setId(1L);
        company.setName("Test Company");

        job = new Job();
        job.setId(1L);
        job.setTitle("Test Job");
        job.setCompany(company);

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
    }

    @Test
    void testApplyToJob_Success() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(applicationRepository.save(any(Application.class))).thenAnswer(i -> i.getArgument(0));

        boolean result = jobService.applyToJob(1L, user);
        assertTrue(result);
        verify(applicationRepository, times(1)).save(any(Application.class));
    }

    @Test
    void testApplyToJob_JobNotFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = jobService.applyToJob(1L, user);
        assertFalse(result);
        verify(applicationRepository, never()).save(any());
    }

    @Test
    void testShowAllJobs() {
        when(jobRepository.findAll()).thenReturn(List.of(job));

        List<Job> jobs = jobService.showAllJobs();
        assertEquals(1, jobs.size());
        assertEquals("Test Job", jobs.get(0).getTitle());
    }

    @Test
    void testCreateNewJob_Success() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(jobRepository.save(any(Job.class))).thenAnswer(i -> i.getArgument(0));

        boolean created = jobService.createNewJob(job);
        assertTrue(created);
        verify(jobRepository, times(1)).save(job);
    }

    @Test
    void testCreateNewJob_Fail_NullCompany() {
        job.setCompany(null);
        boolean created = jobService.createNewJob(job);
        assertFalse(created);
        verify(jobRepository, never()).save(any());
    }

    @Test
    void testCreateNewJob_Fail_InvalidCompany() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        boolean created = jobService.createNewJob(job);
        assertFalse(created);
        verify(jobRepository, never()).save(any());
    }

    @Test
    void testGetJobById_Found() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        Job found = jobService.getJobById(1L);
        assertNotNull(found);
        assertEquals("Test Job", found.getTitle());
    }

    @Test
    void testGetJobById_NotFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());
        Job found = jobService.getJobById(1L);
        assertNull(found);
    }

    @Test
    void testDeleteJobById_Success() {
        doNothing().when(jobRepository).deleteById(1L);
        boolean deleted = jobService.deleteJobById(1L);
        assertTrue(deleted);
        verify(jobRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteJobById_Failure() {
        doThrow(new RuntimeException("DB error")).when(jobRepository).deleteById(1L);
        boolean deleted = jobService.deleteJobById(1L);
        assertFalse(deleted);
        verify(jobRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateJobById_Success() {
        Job updatedJob = new Job();
        updatedJob.setTitle("Updated Title");
        updatedJob.setDescription("Updated Description");
        updatedJob.setLocation("Updated Location");
        updatedJob.setMinSalary("1000");
        updatedJob.setMaxSalary("2000");

        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(jobRepository.save(any(Job.class))).thenAnswer(i -> i.getArgument(0));

        boolean updated = jobService.updateJobById(1L, updatedJob);
        assertTrue(updated);
        verify(jobRepository, times(1)).save(job);
        assertEquals("Updated Title", job.getTitle());
        assertEquals("Updated Description", job.getDescription());
        assertEquals("Updated Location", job.getLocation());
        assertEquals("1000", job.getMinSalary());
        assertEquals("2000", job.getMaxSalary());
    }

    @Test
    void testUpdateJobById_NotFound() {
        Job updatedJob = new Job();
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        boolean updated = jobService.updateJobById(1L, updatedJob);
        assertFalse(updated);
        verify(jobRepository, never()).save(any());
    }
}
