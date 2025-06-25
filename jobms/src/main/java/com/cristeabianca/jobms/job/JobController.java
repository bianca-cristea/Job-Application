package com.cristeabianca.jobms.job;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> showAllJobs() {
        List<Job> jobs = jobService.showAllJobs();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createNewJob(@RequestBody Job job) {
        boolean result = jobService.createNewJob(job);
        if (result) {
            return new ResponseEntity<>("Job created.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Job could not be created.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        if (job != null) {
            return new ResponseEntity<>(job, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id) {
        boolean deleted = jobService.deleteJobById(id);
        if (deleted) {
            return new ResponseEntity<>("Job deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Job could not be deleted.", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJobById(@PathVariable Long id, @RequestBody Job job) {
        boolean updated = jobService.updateJobById(id, job);
        if (updated) {
            return new ResponseEntity<>("Job updated.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Job could not be updated.", HttpStatus.NOT_FOUND);
        }
    }
}
