package com.cristeabianca.job_application.job;


import com.cristeabianca.job_application.job.JobService;
import com.cristeabianca.job_application.user.User;
import com.cristeabianca.job_application.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobControllerIntegrationTest {

    @Mock
    private JobService jobService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JobController jobController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showAllJobs_returnsJobList() {
        List<Job> jobs = List.of(new Job(), new Job());
        when(jobService.showAllJobs()).thenReturn(jobs);

        ResponseEntity<List<Job>> response = jobController.showAllJobs();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(jobService).showAllJobs();
    }

    @Test
    void createNewJob_success_returnsCreated() {
        Job job = new Job();
        when(jobService.createNewJob(job)).thenReturn(true);

        ResponseEntity<String> response = jobController.createNewJob(job);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Job created.", response.getBody());
        verify(jobService).createNewJob(job);
    }

    @Test
    void createNewJob_failure_returnsNotFound() {
        Job job = new Job();
        when(jobService.createNewJob(job)).thenReturn(false);

        ResponseEntity<String> response = jobController.createNewJob(job);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Job could not be created.", response.getBody());
        verify(jobService).createNewJob(job);
    }

    @Test
    void applyToJob_userFoundAndApplied_returnsCreated() {
        Long jobId = 1L;
        String username = "user1";
        User user = new User();

        Principal principal = () -> username;
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(jobService.applyToJob(jobId, user)).thenReturn(true);

        ResponseEntity<String> response = jobController.applyToJob(jobId, principal);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Applied successfully", response.getBody());
        verify(userRepository).findByUsername(username);
        verify(jobService).applyToJob(jobId, user);
    }

    @Test
    void applyToJob_userNotFound_returnsUnauthorized() {
        Long jobId = 1L;
        String username = "user1";

        Principal principal = () -> username;
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        ResponseEntity<String> response = jobController.applyToJob(jobId, principal);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("User not found", response.getBody());
        verify(userRepository).findByUsername(username);
        verify(jobService, never()).applyToJob(anyLong(), any());
    }

    @Test
    void applyToJob_applyFails_returnsBadRequest() {
        Long jobId = 1L;
        String username = "user1";
        User user = new User();

        Principal principal = () -> username;
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(jobService.applyToJob(jobId, user)).thenReturn(false);

        ResponseEntity<String> response = jobController.applyToJob(jobId, principal);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Failed to apply", response.getBody());
        verify(userRepository).findByUsername(username);
        verify(jobService).applyToJob(jobId, user);
    }

    @Test
    void findJobById_found_returnsJob() {
        Long jobId = 1L;
        Job job = new Job();

        when(jobService.getJobById(jobId)).thenReturn(job);

        ResponseEntity<Job> response = jobController.findJobById(jobId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(job, response.getBody());
        verify(jobService).getJobById(jobId);
    }

    @Test
    void findJobById_notFound_returnsNotFound() {
        Long jobId = 1L;
        when(jobService.getJobById(jobId)).thenReturn(null);

        ResponseEntity<Job> response = jobController.findJobById(jobId);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(jobService).getJobById(jobId);
    }

    @Test
    void deleteJobById_success_returnsOk() {
        Long jobId = 1L;
        when(jobService.deleteJobById(jobId)).thenReturn(true);

        ResponseEntity<String> response = jobController.deleteJobById(jobId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("job deleted", response.getBody());
        verify(jobService).deleteJobById(jobId);
    }

    @Test
    void deleteJobById_failure_returnsNotFound() {
        Long jobId = 1L;
        when(jobService.deleteJobById(jobId)).thenReturn(false);

        ResponseEntity<String> response = jobController.deleteJobById(jobId);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("job could not be deleted", response.getBody());
        verify(jobService).deleteJobById(jobId);
    }

    @Test
    void updateJobById_success_returnsOk() {
        Long jobId = 1L;
        Job job = new Job();

        when(jobService.updateJobById(jobId, job)).thenReturn(true);

        ResponseEntity<String> response = jobController.updateJobById(jobId, job);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated", response.getBody());
        verify(jobService).updateJobById(jobId, job);
    }

    @Test
    void updateJobById_failure_returnsNotFound() {
        Long jobId = 1L;
        Job job = new Job();

        when(jobService.updateJobById(jobId, job)).thenReturn(false);

        ResponseEntity<String> response = jobController.updateJobById(jobId, job);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Could not update", response.getBody());
        verify(jobService).updateJobById(jobId, job);
    }
}