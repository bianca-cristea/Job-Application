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

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    private User user;
    private Job job;
    private Application application;

    @BeforeEach
    void setUp() {
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
    void getAllApplications_shouldReturnAllApplications() {
        List<Application> applications = List.of(application);
        when(applicationRepository.findAll()).thenReturn(applications);

        List<Application> result = applicationService.getAllApplications();

        assertEquals(1, result.size());
        verify(applicationRepository, times(1)).findAll();
    }

    @Test
    void getApplicationsaByUser_shouldReturnApplicationsForUser() {
        List<Application> applications = List.of(application);
        when(applicationRepository.findByUserId(user.getId())).thenReturn(applications);

        List<Application> result = applicationService.getApplicationsByUser(user.getId());

        assertEquals(1, result.size());
        verify(applicationRepository).findByUserId(user.getId());
    }

    @Test
    void getApplicationsByJob_shouldReturnApplicationsForJob() {
        List<Application> applications = List.of(application);
        when(applicationRepository.findByJobId(job.getId())).thenReturn(applications);

        List<Application> result = applicationService.getApplicationsByJob(job.getId());

        assertEquals(1, result.size());
        verify(applicationRepository).findByJobId(job.getId());
    }

    @Test
    void getApplicationById_whenExists_shouldReturnApplication() {
        when(applicationRepository.findById(application.getId())).thenReturn(Optional.of(application));

        Application result = applicationService.getApplicationById(application.getId());

        assertNotNull(result);
        assertEquals(application.getId(), result.getId());
    }

    @Test
    void getApplicationById_whenNotExists_shouldReturnNull() {
        when(applicationRepository.findById(2L)).thenReturn(Optional.empty());

        Application result = applicationService.getApplicationById(2L);

        assertNull(result);
    }

    @Test
    void createApplication_whenUserAndJobExist_shouldCreateAndReturnTrue() {
        Application newApp = new Application();
        newApp.setStatus("Pending");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(jobRepository.findById(job.getId())).thenReturn(Optional.of(job));
        when(applicationRepository.save(any(Application.class))).thenAnswer(i -> i.getArgument(0));

        boolean created = applicationService.createApplication(newApp, user.getId(), job.getId());

        assertTrue(created);
        assertEquals(user, newApp.getUser());
        assertEquals(job, newApp.getJob());
        verify(applicationRepository).save(newApp);
    }

    @Test
    void createApplication_whenUserNotFound_shouldReturnFalse() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        when(jobRepository.findById(job.getId())).thenReturn(Optional.of(job));

        boolean created = applicationService.createApplication(application, user.getId(), job.getId());

        assertFalse(created);
        verify(applicationRepository, never()).save(any());
    }

    @Test
    void createApplication_whenJobNotFound_shouldReturnFalse() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(jobRepository.findById(job.getId())).thenReturn(Optional.empty());

        boolean created = applicationService.createApplication(application, user.getId(), job.getId());

        assertFalse(created);
        verify(applicationRepository, never()).save(any());
    }

    @Test
    void updateApplication_whenExists_shouldUpdateAndReturnTrue() {
        Application updatedApp = new Application();
        updatedApp.setStatus("Accepted");

        when(applicationRepository.findById(application.getId())).thenReturn(Optional.of(application));
        when(applicationRepository.save(any(Application.class))).thenAnswer(i -> i.getArgument(0));

        boolean updated = applicationService.updateApplication(application.getId(), updatedApp);

        assertTrue(updated);
        assertEquals("Accepted", application.getStatus());
        verify(applicationRepository).save(application);
    }

    @Test
    void updateApplication_whenNotExists_shouldReturnFalse() {
        when(applicationRepository.findById(2L)).thenReturn(Optional.empty());

        boolean updated = applicationService.updateApplication(2L, application);

        assertFalse(updated);
        verify(applicationRepository, never()).save(any());
    }

    @Test
    void deleteApplication_whenExists_shouldDeleteAndReturnTrue() {
        when(applicationRepository.existsById(application.getId())).thenReturn(true);
        doNothing().when(applicationRepository).deleteById(application.getId());

        boolean deleted = applicationService.deleteApplication(application.getId());

        assertTrue(deleted);
        verify(applicationRepository).deleteById(application.getId());
    }

    @Test
    void deleteApplication_whenNotExists_shouldReturnFalse() {
        when(applicationRepository.existsById(2L)).thenReturn(false);

        boolean deleted = applicationService.deleteApplication(2L);

        assertFalse(deleted);
        verify(applicationRepository, never()).deleteById(anyLong());
    }
}
