package com.cristeabianca.job_application.job;

import com.cristeabianca.job_application.job.impl.JobServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobServiceImplTest {

    private JobRepository jobRepository;
    private JobServiceImpl jobService;

    @BeforeEach
    void setUp() {
        jobRepository = mock(JobRepository.class);
        jobService = new JobServiceImpl(jobRepository);
    }

    @Test
    void testCreateNewJob_success() {
        Job job = new Job();
        job.setTitle("QA Engineer");

        when(jobRepository.save(ArgumentMatchers.any(Job.class))).thenReturn(job);

        boolean result = jobService.createNewJob(job);

        assertTrue(result);
        verify(jobRepository, times(1)).save(job);
    }

    @Test
    void testShowAllJobs_returnsJobs() {
        when(jobRepository.findAll()).thenReturn(Collections.singletonList(new Job()));

        assertEquals(1, jobService.showAllJobs().size());
        verify(jobRepository, times(1)).findAll();
    }

    @Test
    void testGetJobById_found() {
        Job job = new Job();
        job.setId(1L);
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        Job found = jobService.getJobById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
    }

    @Test
    void testUpdateJobById_success() {
        Job existing = new Job();
        existing.setId(1L);
        existing.setTitle("Old");

        Job updated = new Job();
        updated.setTitle("New");
        updated.setDescription("Desc");
        updated.setLocation("Bucharest");
        updated.setMinSalary("1000");
        updated.setMaxSalary("2000");

        when(jobRepository.findById(1L)).thenReturn(Optional.of(existing));

        boolean result = jobService.updateJobById(1L, updated);

        assertTrue(result);
        verify(jobRepository, times(1)).save(existing);
    }

    @Test
    void testDeleteJobById_success() {
        doNothing().when(jobRepository).deleteById(1L);

        boolean result = jobService.deleteJobById(1L);

        assertTrue(result);
        verify(jobRepository, times(1)).deleteById(1L);
    }
}
