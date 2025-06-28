package com.cristeabianca.job_application.application;

import com.cristeabianca.job_application.application.impl.ApplicationServiceImpl;
import com.cristeabianca.job_application.job.Job;
import com.cristeabianca.job_application.job.JobRepository;
import com.cristeabianca.job_application.user.User;
import com.cristeabianca.job_application.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationServiceImplTest {

    @Mock private ApplicationRepository applicationRepository;
    @Mock private UserRepository userRepository;
    @Mock private JobRepository jobRepository;

    @InjectMocks private ApplicationServiceImpl applicationService;

    private User user;
    private Job job;
    private Application application;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        job = new Job();
        job.setId(1L);

        application = new Application();
        application.setId(1L);
        application.setStatus("Pending");
        application.setUser(user);
        application.setJob(job);
    }

    @Test
    void shouldReturnAllApplications() {
        when(applicationRepository.findAll()).thenReturn(List.of(application));

        List<Application> result = applicationService.getAllApplications();

        assertEquals(1, result.size());
        verify(applicationRepository).findAll();
    }

    @Test
    void shouldReturnApplicationsByUser() {
        when(applicationRepository.findByUserId(1L)).thenReturn(List.of(application));

        List<Application> result = applicationService.getApplicationsByUser(1L);

        assertEquals(1, result.size());
        verify(applicationRepository).findByUserId(1L);
    }

    @Test
    void shouldReturnApplicationsByJob() {
        when(applicationRepository.findByJobId(1L)).thenReturn(List.of(application));

        List<Application> result = applicationService.getApplicationsByJob(1L);

        assertEquals(1, result.size());
        verify(applicationRepository).findByJobId(1L);
    }

    @Test
    void shouldReturnApplicationById() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));

        Application result = applicationService.getApplicationById(1L);

        assertEquals(application, result);
    }

    @Test
    void shouldReturnNullWhenApplicationNotFound() {
        when(applicationRepository.findById(2L)).thenReturn(Optional.empty());

        Application result = applicationService.getApplicationById(2L);

        assertNull(result);
    }

    @Test
    void shouldCreateApplicationWhenUserAndJobExist() {
        Application newApp = new Application();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(applicationRepository.save(any(Application.class))).thenAnswer(i -> i.getArgument(0));

        boolean created = applicationService.createApplication(newApp, 1L, 1L);

        assertTrue(created);
        assertEquals("Pending", newApp.getStatus());
        verify(applicationRepository).save(newApp);
    }

    @Test
    void shouldNotCreateApplicationWhenUserMissing() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        boolean result = applicationService.createApplication(application, 1L, 1L);

        assertFalse(result);
        verify(applicationRepository, never()).save(any());
    }

    @Test
    void shouldNotCreateApplicationWhenJobMissing() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = applicationService.createApplication(application, 1L, 1L);

        assertFalse(result);
        verify(applicationRepository, never()).save(any());
    }

    @Test
    void shouldUpdateApplicationWhenExists() {
        Application update = new Application();
        update.setStatus("Accepted");

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));
        when(applicationRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        boolean result = applicationService.updateApplication(1L, update);

        assertTrue(result);
        assertEquals("Accepted", application.getStatus());
    }

    @Test
    void shouldNotUpdateWhenApplicationNotFound() {
        when(applicationRepository.findById(2L)).thenReturn(Optional.empty());

        boolean result = applicationService.updateApplication(2L, application);

        assertFalse(result);
        verify(applicationRepository, never()).save(any());
    }

    @Test
    void shouldDeleteApplicationIfExists() {
        when(applicationRepository.existsById(1L)).thenReturn(true);

        boolean result = applicationService.deleteApplication(1L);

        assertTrue(result);
        verify(applicationRepository).deleteById(1L);
    }

    @Test
    void shouldNotDeleteIfNotExists() {
        when(applicationRepository.existsById(2L)).thenReturn(false);

        boolean result = applicationService.deleteApplication(2L);

        assertFalse(result);
        verify(applicationRepository, never()).deleteById(anyLong());
    }
}
