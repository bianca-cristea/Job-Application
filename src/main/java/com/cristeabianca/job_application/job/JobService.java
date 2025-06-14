package com.cristeabianca.job_application.job;

import com.cristeabianca.job_application.application.Application;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public interface JobService {

    List<Job> showAllJobs();
    boolean createNewJob(Job job);
    Job getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean updateJobById(Long id,Job job);

    boolean applyToJob(Long id, @Valid Application application);
}