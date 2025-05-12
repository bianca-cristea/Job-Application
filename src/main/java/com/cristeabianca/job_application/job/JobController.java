package com.cristeabianca.job_application.job;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {


    JobService jobService;

    public JobController(JobService jobService){
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> showAllJobs(){
        return new ResponseEntity<>(jobService.showAllJobs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createNewJob(@RequestBody Job job){
        boolean result = jobService.createNewJob(job);
        return result?new ResponseEntity<>("Job created.",HttpStatus.CREATED):new ResponseEntity<>("Job could not be created.",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findJobById(@PathVariable Long id){
        Job job = jobService.getJobById(id);
        if(job!=null){
           return new ResponseEntity<>(job,HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        boolean response = jobService.deleteJobById(id);
        return response?new ResponseEntity<>("job deleted",HttpStatus.OK):new ResponseEntity<>("job could not be deleted",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJobById(@PathVariable Long id,@RequestBody Job job){
        if(jobService.updateJobById(id,job)){
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        }
        else return new ResponseEntity<>("Could not update",HttpStatus.NOT_FOUND);
    }

}
