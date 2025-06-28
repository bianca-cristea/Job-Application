package com.cristeabianca.job_application.interview;

import com.cristeabianca.job_application.application.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InterviewControllerTest {

    @Mock
    private InterviewService interviewService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private InterviewRepository interviewRepository;

    @InjectMocks
    private InterviewController interviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByApplication_found() {
        Interview interview = new Interview();
        when(interviewService.getInterviewByApplication(1L)).thenReturn(interview);

        ResponseEntity<Interview> response = interviewController.getByApplication(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(interview, response.getBody());
    }

    @Test
    void getByApplication_notFound() {
        when(interviewService.getInterviewByApplication(1L)).thenReturn(null);

        ResponseEntity<Interview> response = interviewController.getByApplication(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllInterviews() {
        List<Interview> interviews = List.of(new Interview(), new Interview());
        when(interviewRepository.findAll()).thenReturn(interviews);

        List<Interview> response = interviewController.getAllInterviews();
        assertEquals(2, response.size());
        assertEquals(interviews, response);
    }

    @Test
    void create_validInterview_success() {
        Interview interview = new Interview();
        interview.setScheduledAt(LocalDateTime.now());

        when(interviewService.createInterview(1L, interview)).thenReturn(true);

        ResponseEntity<String> response = interviewController.create(1L, interview);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Interview created", response.getBody());
    }

    @Test
    void create_invalidInterview_missingDate() {
        Interview interview = new Interview();

        ResponseEntity<String> response = interviewController.create(1L, interview);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Scheduled date must be provided by admin", response.getBody());
    }

    @Test
    void create_failedCreation() {
        Interview interview = new Interview();
        interview.setScheduledAt(LocalDateTime.now());

        when(interviewService.createInterview(1L, interview)).thenReturn(false);

        ResponseEntity<String> response = interviewController.create(1L, interview);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed", response.getBody());
    }

    @Test
    void deleteInterview_found() {
        when(interviewService.deleteInterview(1L)).thenReturn(true);

        ResponseEntity<Void> response = interviewController.deleteInterview(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteInterview_notFound() {
        when(interviewService.deleteInterview(1L)).thenReturn(false);

        ResponseEntity<Void> response = interviewController.deleteInterview(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllInterviewsDTO() {
        List<InterviewDTO> dtos = List.of(new InterviewDTO(1L, LocalDateTime.now(), "Job", "User", "Company"));
        when(interviewService.getAllInterviewsDTO()).thenReturn(dtos);

        ResponseEntity<List<InterviewDTO>> response = interviewController.getAllInterviewsDTO();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }

    @Test
    void getAllGroupedByCompany() {
        Map<String, List<Interview>> grouped = new HashMap<>();
        grouped.put("Company1", List.of(new Interview()));
        when(interviewService.getAllGroupedByCompany()).thenReturn(grouped);

        ResponseEntity<?> response = interviewController.getAllGroupedByCompany();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(grouped, response.getBody());
    }

    @Test
    void updateInterview_foundAndValid() {
        Long id = 1L;
        Interview updated = new Interview();
        updated.setScheduledAt(LocalDateTime.now());

        Interview existing = new Interview();
        when(interviewRepository.findById(id)).thenReturn(Optional.of(existing));

        ResponseEntity<String> response = interviewController.updateInterview(id, updated);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Interview updated", response.getBody());

        verify(interviewRepository).save(existing);
        assertEquals(updated.getScheduledAt(), existing.getScheduledAt());
    }

    @Test
    void updateInterview_notFound() {
        Long id = 1L;
        Interview updated = new Interview();
        updated.setScheduledAt(LocalDateTime.now());

        when(interviewRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<String> response = interviewController.updateInterview(id, updated);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Interview not found", response.getBody());
    }

    @Test
    void updateInterview_missingScheduledAt() {
        Long id = 1L;
        Interview updated = new Interview();

        Interview existing = new Interview();
        when(interviewRepository.findById(id)).thenReturn(Optional.of(existing));

        ResponseEntity<String> response = interviewController.updateInterview(id, updated);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Scheduled date must be provided", response.getBody());
    }

    @Test
    void getUserInterviews_authorized() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user");
        List<Interview> interviews = List.of(new Interview());
        when(interviewService.getInterviewsByUsername("user")).thenReturn(interviews);

        ResponseEntity<List<Interview>> response = interviewController.getUserInterviews(userDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(interviews, response.getBody());
    }

    @Test
    void getUserInterviews_unauthorized() {
        ResponseEntity<List<Interview>> response = interviewController.getUserInterviews(null);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
