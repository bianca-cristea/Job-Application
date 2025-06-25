package com.cristeabianca.jobms.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Application>> getApplicationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(applicationService.getApplicationsByUser(userId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getApplicationsByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJob(jobId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        Application app = applicationService.getApplicationById(id);
        if (app == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(app);
    }

    @PostMapping
    public ResponseEntity<String> createApplication(@RequestBody Application application,
                                                    @RequestParam Long userId,
                                                    @RequestParam Long jobId) {
        boolean created = applicationService.createApplication(application, userId, jobId);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Application created successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create application");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateApplication(@PathVariable Long id, @RequestBody Application application) {
        boolean updated = applicationService.updateApplication(id, application);
        if (updated) {
            return ResponseEntity.ok("Application updated successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable Long id) {
        boolean deleted = applicationService.deleteApplication(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
