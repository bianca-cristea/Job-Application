package com.cristeabianca.job_application.interview;

import com.cristeabianca.job_application.application.Application;
import com.cristeabianca.job_application.application.ApplicationRepository;
import com.cristeabianca.job_application.interview.*;
import com.cristeabianca.job_application.interview.impl.InterviewServiceImpl;
import com.cristeabianca.job_application.job.Job;
import com.cristeabianca.job_application.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InterviewServiceImplTest {

    @Mock private InterviewRepository interviewRepository;
    @Mock private ApplicationRepository applicationRepository;
    @InjectMocks private InterviewServiceImpl interviewService;

    private Application application;
    private Interview interview;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Job job = new Job();
        job.setTitle("Backend Developer");

        User user = new User();
        user.setId(1L);
        user.setUsername("johndoe");

        application = new Application();
        application.setId(10L);
        application.setUser(user);
        application.setJob(job);

        interview = new Interview();
        interview.setId(1L);
        interview.setScheduledAt(LocalDateTime.now());
        interview.setApplication(application);
        interview.setJob(job);
    }

    @Test
    void shouldGetInterviewByApplication() {
        when(interviewRepository.findByApplicationId(10L)).thenReturn(interview);
        Interview result = interviewService.getInterviewByApplication(10L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldCreateInterviewSuccessfully() {
        when(applicationRepository.findById(10L)).thenReturn(Optional.of(application));
        boolean created = interviewService.createInterview(10L, interview);
        assertTrue(created);
        verify(interviewRepository).save(interview);
    }

    @Test
    void shouldNotCreateInterviewWhenApplicationNotFound() {
        when(applicationRepository.findById(99L)).thenReturn(Optional.empty());
        boolean created = interviewService.createInterview(99L, interview);
        assertFalse(created);
    }

    @Test
    void shouldUpdateInterviewSuccessfully() {
        when(interviewRepository.findById(1L)).thenReturn(Optional.of(interview));
        Interview updated = new Interview();
        updated.setScheduledAt(LocalDateTime.now().plusDays(1));
        updated.setJob(new Job());

        boolean result = interviewService.updateInterview(1L, updated);
        assertTrue(result);
        verify(interviewRepository).save(interview);
    }

    @Test
    void shouldNotUpdateInterviewIfNotFound() {
        when(interviewRepository.findById(999L)).thenReturn(Optional.empty());
        Interview updated = new Interview();
        boolean result = interviewService.updateInterview(999L, updated);
        assertFalse(result);
    }

    @Test
    void shouldDeleteInterviewSuccessfully() {
        when(interviewRepository.findById(1L)).thenReturn(Optional.of(interview));
        boolean result = interviewService.deleteInterview(1L);
        assertTrue(result);
        verify(interviewRepository).deleteById(1L);
    }

    @Test
    void shouldNotDeleteInterviewIfNotFound() {
        when(interviewRepository.findById(999L)).thenReturn(Optional.empty());
        boolean result = interviewService.deleteInterview(999L);
        assertFalse(result);
    }

    @Test
    void shouldGetAllGroupedByCompany() {
        Job job = new Job();
        job.setCompany(new com.cristeabianca.job_application.company.Company());
        job.getCompany().setName("IBM");
        application.setJob(job);
        interview.setApplication(application);
        when(interviewRepository.findAll()).thenReturn(List.of(interview));

        Map<String, List<Interview>> grouped = interviewService.getAllGroupedByCompany();
        assertTrue(grouped.containsKey("IBM"));
    }

    @Test
    void shouldGetAllInterviewsDTO() {
        when(interviewRepository.findAll()).thenReturn(List.of(interview));
        List<InterviewDTO> dtos = interviewService.getAllInterviewsDTO();
        assertEquals(1, dtos.size());
        assertEquals("johndoe", dtos.get(0).getCandidateName());
    }
}
