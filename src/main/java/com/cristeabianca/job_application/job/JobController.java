package com.cristeabianca.job_application.job;
import com.cristeabianca.job_application.application.Application;
import com.cristeabianca.job_application.user.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.cristeabianca.job_application.user.User;
import com.cristeabianca.job_application.company.Company;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;
    private final UserRepository userRepository;

    public JobController(JobService jobService, UserRepository userRepository){
        this.jobService = jobService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Job>> showAllJobs(){
        return new ResponseEntity<>(jobService.showAllJobs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createNewJob(@RequestBody @Valid Job job){
        boolean result = jobService.createNewJob(job);
        return result?new ResponseEntity<>("Job created.",HttpStatus.CREATED):new ResponseEntity<>("Job could not be created.",HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<String> applyToJob(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }

        boolean applied = jobService.applyToJob(id, user);
        return applied
                ? new ResponseEntity<>("Applied successfully", HttpStatus.CREATED)
                : new ResponseEntity<>("Failed to apply", HttpStatus.BAD_REQUEST);
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