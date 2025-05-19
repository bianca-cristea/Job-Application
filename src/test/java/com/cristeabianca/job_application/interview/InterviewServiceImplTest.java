package com.cristeabianca.job_application.interview;

import com.cristeabianca.job_application.application.Application;
import com.cristeabianca.job_application.application.ApplicationRepository;
import com.cristeabianca.job_application.interview.Interview;
import com.cristeabianca.job_application.interview.InterviewRepository;
import com.cristeabianca.job_application.interview.impl.InterviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InterviewServiceImplTest {

    @Mock
    private InterviewRepository interviewRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private InterviewServiceImpl interviewService;

    private Application application;
    private Interview interview;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        application = new Application();
        application.setId(1L);

        interview = new Interview();
        interview.setId(1L);
        interview.setLocation("Room 101");
        interview.setScheduledAt(LocalDateTime.now());
        interview.setApplication(application);
    }

    @Test
    void getInterviewByApplication_shouldReturnInterview() {
        when(interviewRepository.findByApplicationId(1L)).thenReturn(interview);

        Interview result = interviewService.getInterviewByApplication(1L);

        assertNotNull(result);
        assertEquals("Room 101", result.getLocation());
        verify(interviewRepository).findByApplicationId(1L);
    }

    @Test
    void getInterviewByApplication_whenNone_shouldReturnNull() {
        when(interviewRepository.findByApplicationId(2L)).thenReturn(null);

        Interview result = interviewService.getInterviewByApplication(2L);

        assertNull(result);
    }

    @Test
    void createInterview_whenApplicationExists_shouldSaveAndReturnTrue() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));
        when(interviewRepository.save(any(Interview.class))).thenReturn(interview);

        boolean created = interviewService.createInterview(1L, interview);

        assertTrue(created);
        verify(interviewRepository).save(interview);
        assertEquals(application, interview.getApplication());
    }

    @Test
    void createInterview_whenApplicationNotFound_shouldReturnFalse() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.empty());

        boolean created = interviewService.createInterview(1L, interview);

        assertFalse(created);
        verify(interviewRepository, never()).save(any());
    }

    @Test
    void updateInterview_whenExists_shouldUpdateAndReturnTrue() {
        Interview updated = new Interview();
        updated.setLocation("Room 202");
        updated.setScheduledAt(LocalDateTime.now().plusDays(1));

        when(interviewRepository.findById(1L)).thenReturn(Optional.of(interview));
        when(interviewRepository.save(any(Interview.class))).thenReturn(interview);

        boolean updatedResult = interviewService.updateInterview(1L, updated);

        assertTrue(updatedResult);
        assertEquals("Room 202", interview.getLocation());
        verify(interviewRepository).save(interview);
    }

    @Test
    void updateInterview_whenNotExists_shouldReturnFalse() {
        when(interviewRepository.findById(2L)).thenReturn(Optional.empty());

        boolean updatedResult = interviewService.updateInterview(2L, interview);

        assertFalse(updatedResult);
        verify(interviewRepository, never()).save(any());
    }

    @Test
    void deleteInterview_whenExists_shouldDeleteAndReturnTrue() {
        when(interviewRepository.existsById(1L)).thenReturn(true);
        doNothing().when(interviewRepository).deleteById(1L);

        boolean deleted = interviewService.deleteInterview(1L);

        assertTrue(deleted);
        verify(interviewRepository).deleteById(1L);
    }

    @Test
    void deleteInterview_whenNotExists_shouldReturnFalse() {
        when(interviewRepository.existsById(1L)).thenReturn(false);

        boolean deleted = interviewService.deleteInterview(1L);

        assertFalse(deleted);
        verify(interviewRepository, never()).deleteById(any());
    }
}
