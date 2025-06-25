package com.cristeabianca.jobms.interview;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<Interview> getByApplication(@PathVariable Long applicationId) {
        Interview interview = interviewService.getInterviewByApplication(applicationId);
        return interview != null
                ? new ResponseEntity<>(interview, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Interview>> getAllInterviews() {
        return ResponseEntity.ok(interviewService.getAllInterviews());
    }

    @PostMapping("/application/{applicationId}")
    public ResponseEntity<String> create(@PathVariable Long applicationId, @RequestBody Interview interview) {
        if (interview.getScheduledAt() == null) {
            return new ResponseEntity<>("Scheduled date must be provided", HttpStatus.BAD_REQUEST);
        }
        boolean created = interviewService.createInterview(applicationId, interview);
        return created
                ? new ResponseEntity<>("Interview created", HttpStatus.CREATED)
                : new ResponseEntity<>("Failed to create interview", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        boolean deleted = interviewService.deleteInterview(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{interviewId}")
    public ResponseEntity<String> updateInterview(@PathVariable Long interviewId, @RequestBody Interview updatedInterview) {
        if (updatedInterview.getScheduledAt() == null) {
            return new ResponseEntity<>("Scheduled date must be provided", HttpStatus.BAD_REQUEST);
        }

        boolean updated = interviewService.updateInterview(interviewId, updatedInterview);
        if (updated) {
            return new ResponseEntity<>("Interview updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Interview not found", HttpStatus.NOT_FOUND);
        }
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
