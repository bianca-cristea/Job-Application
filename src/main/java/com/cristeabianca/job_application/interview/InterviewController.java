package com.cristeabianca.job_application.interview;

import com.cristeabianca.job_application.application.ApplicationRepository;
import com.cristeabianca.job_application.application.ApplicationService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    private final InterviewService interviewService;
    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;

    public InterviewController(InterviewService interviewService,
                                 ApplicationRepository applicationRepository,
                                 InterviewRepository interviewRepository) {
        this.interviewService = interviewService;
        this.applicationRepository = applicationRepository;
        this.interviewRepository = interviewRepository;
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<Interview> getByApplication(@PathVariable Long applicationId) {
        Interview interview = interviewService.getInterviewByApplication(applicationId);
        return interview != null ? new ResponseEntity<>(interview, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping
    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    @PostMapping("/application/{applicationId}")
    public ResponseEntity<String> create(@PathVariable Long applicationId, @RequestBody Interview interview) {
        boolean result = interviewService.createInterview(applicationId, interview);
        return result ? new ResponseEntity<>("Interview created", HttpStatus.CREATED) :
                new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Interview interview) {
        return interviewService.updateInterview(id, interview) ?
                new ResponseEntity<>("Interview updated", HttpStatus.OK) :
                new ResponseEntity<>("Interview not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return interviewService.deleteInterview(id) ?
                new ResponseEntity<>("Deleted", HttpStatus.OK) :
                new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllGroupedByCompany() {
        return new ResponseEntity<>(interviewService.getAllGroupedByCompany(), HttpStatus.OK);
    }
    @PutMapping("/{interviewId}")
    public ResponseEntity<String> updateInterview(@PathVariable Long interviewId, @RequestBody Interview updatedInterview) {
        Optional<Interview> optionalInterview = interviewRepository.findById(interviewId);
        if (optionalInterview.isEmpty()) {
            return new ResponseEntity<>("Interview not found", HttpStatus.NOT_FOUND);
        }
        Interview interview = optionalInterview.get();
        interview.setScheduledAt(updatedInterview.getScheduledAt());
        interview.setLocation(updatedInterview.getLocation());
        interviewRepository.save(interview);
        return new ResponseEntity<>("Interview updated", HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Interview>> getUserInterviews(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Interview> interviews = interviewService.getInterviewsByUsername(userDetails.getUsername());
        return new ResponseEntity<>(interviews, HttpStatus.OK);
    }
}
