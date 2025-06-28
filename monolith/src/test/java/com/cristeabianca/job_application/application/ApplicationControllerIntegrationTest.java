package com.cristeabianca.job_application.application;

import com.cristeabianca.job_application.interview.Interview;
import com.cristeabianca.job_application.interview.InterviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ApplicationControllerIntegrationTest {

    @Mock
    private ApplicationService applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private InterviewRepository interviewRepository;

    @InjectMocks
    private ApplicationController applicationController;

    private Application sampleApplication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sampleApplication = new Application();
        sampleApplication.setId(1L);
        sampleApplication.setStatus("Pending");
    }

    @Test
    void testGetAllApplications() {
        when(applicationService.getAllApplications()).thenReturn(List.of(sampleApplication));

        ResponseEntity<List<Application>> response = applicationController.getAllApplications();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetById_Found() {
        when(applicationService.getApplicationById(1L)).thenReturn(sampleApplication);

        ResponseEntity<Application> response = applicationController.getById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleApplication, response.getBody());
    }

    @Test
    void testGetById_NotFound() {
        when(applicationService.getApplicationById(2L)).thenReturn(null);

        ResponseEntity<Application> response = applicationController.getById(2L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetByUser() {
        when(applicationService.getApplicationsByUser(1L)).thenReturn(List.of(sampleApplication));

        ResponseEntity<List<Application>> response = applicationController.getByUser(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetByJob() {
        when(applicationService.getApplicationsByJob(1L)).thenReturn(List.of(sampleApplication));

        ResponseEntity<List<Application>> response = applicationController.getByJob(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testUpdateApplicationStatus_ApplicationNotFound() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = applicationController.updateApplicationStatus(1L, "Accepted");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Application not found", response.getBody());
    }

    @Test
    void testUpdateApplicationStatus_UpdateStatusAndCreateInterview() {
        sampleApplication.setInterview(null);
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(sampleApplication));
        when(applicationRepository.save(any())).thenReturn(sampleApplication);
        when(interviewRepository.save(any())).thenReturn(new Interview());

        ResponseEntity<String> response = applicationController.updateApplicationStatus(1L, "interview");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Status updated", response.getBody());
        verify(interviewRepository, times(1)).save(any());
        verify(applicationRepository, times(1)).save(sampleApplication);
        assertEquals("interview", sampleApplication.getStatus().toLowerCase());
    }

    @Test
    void testUpdateApplicationStatus_UpdateStatusWithoutInterviewCreation() {
        sampleApplication.setInterview(new Interview());
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(sampleApplication));
        when(applicationRepository.save(any())).thenReturn(sampleApplication);

        ResponseEntity<String> response = applicationController.updateApplicationStatus(1L, "Accepted");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Status updated", response.getBody());
        verify(interviewRepository, never()).save(any());
        verify(applicationRepository, times(1)).save(sampleApplication);
        assertEquals("Accepted", sampleApplication.getStatus());
    }

    @Test
    void testCreate_Success() {
        when(applicationService.createApplication(any(), anyLong(), anyLong())).thenReturn(true);

        ResponseEntity<String> response = applicationController.create(sampleApplication, 1L, 1L);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Application created", response.getBody());
    }

    @Test
    void testCreate_Failure() {
        when(applicationService.createApplication(any(), anyLong(), anyLong())).thenReturn(false);

        ResponseEntity<String> response = applicationController.create(sampleApplication, 1L, 1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to create application", response.getBody());
    }

    @Test
    void testUpdate_Success() {
        when(applicationService.updateApplication(1L, sampleApplication)).thenReturn(true);

        ResponseEntity<String> response = applicationController.update(1L, sampleApplication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated", response.getBody());
    }

    @Test
    void testUpdate_NotFound() {
        when(applicationService.updateApplication(1L, sampleApplication)).thenReturn(false);

        ResponseEntity<String> response = applicationController.update(1L, sampleApplication);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not found", response.getBody());
    }

    @Test
    void testDelete_Success() {
        when(applicationService.deleteApplication(1L)).thenReturn(true);

        ResponseEntity<String> response = applicationController.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted", response.getBody());
    }

    @Test
    void testDelete_NotFound() {
        when(applicationService.deleteApplication(1L)).thenReturn(false);

        ResponseEntity<String> response = applicationController.delete(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not found", response.getBody());
    }
}
