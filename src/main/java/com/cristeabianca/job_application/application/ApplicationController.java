package com.cristeabianca.job_application.application;

import com.cristeabianca.job_application.interview.Interview;
import com.cristeabianca.job_application.interview.InterviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;

    public ApplicationController(ApplicationService applicationService,
                                 ApplicationRepository applicationRepository,
                                 InterviewRepository interviewRepository) {
        this.applicationService = applicationService;
        this.applicationRepository = applicationRepository;
        this.interviewRepository = interviewRepository;
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        return new ResponseEntity<>(applicationService.getAllApplications(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getById(@PathVariable Long id) {
        Application app = applicationService.getApplicationById(id);
        return app != null
                ? new ResponseEntity<>(app, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Application>> getByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(applicationService.getApplicationsByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getByJob(@PathVariable Long jobId) {
        return new ResponseEntity<>(applicationService.getApplicationsByJob(jobId), HttpStatus.OK);
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateApplicationStatus(@PathVariable Long id, @RequestParam String status) {
        Optional<Application> optionalApplication = applicationRepository.findById(id);
        if (optionalApplication.isEmpty()) {
            return new ResponseEntity<>("Application not found", HttpStatus.NOT_FOUND);
        }
        Application application = optionalApplication.get();
        application.setStatus(status);

        if ("interview".equalsIgnoreCase(status) && application.getInterview() == null) {
            Interview interview = new Interview();
            interview.setApplication(application);
            interviewRepository.save(interview);
        }

        applicationRepository.save(application);
        return new ResponseEntity<>("Status updated", HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/job/{jobId}")
    public ResponseEntity<String> create(@RequestBody Application app,
                                         @PathVariable Long userId,
                                         @PathVariable Long jobId) {
        boolean result = applicationService.createApplication(app, userId, jobId);
        return result
                ? new ResponseEntity<>("Application created", HttpStatus.CREATED)
                : new ResponseEntity<>("Failed to create application", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Application app) {
        boolean updated = applicationService.updateApplication(id, app);
        return updated
                ? new ResponseEntity<>("Updated", HttpStatus.OK)
                : new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        boolean deleted = applicationService.deleteApplication(id);
        return deleted
                ? new ResponseEntity<>("Deleted", HttpStatus.OK)
                : new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }
}
