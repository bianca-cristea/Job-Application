package com.cristeabianca.job_application.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
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
