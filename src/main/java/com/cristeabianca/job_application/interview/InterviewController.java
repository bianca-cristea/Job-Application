package com.cristeabianca.job_application.interview;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

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
        return interview != null ? new ResponseEntity<>(interview, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
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


    @GetMapping("/user")
    public ResponseEntity<List<Interview>> getUserInterviews(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Interview> interviews = interviewService.getInterviewsByUsername(userDetails.getUsername());
        return new ResponseEntity<>(interviews, HttpStatus.OK);
    }
}
