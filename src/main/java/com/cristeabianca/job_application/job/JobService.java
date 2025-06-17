package com.cristeabianca.job_application.job;

import com.cristeabianca.job_application.job.Job;
import com.cristeabianca.job_application.user.User;

import java.util.List;

public interface JobService {
    List<Job> showAllJobs();
    boolean createNewJob(Job job);
    Job getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean updateJobById(Long id, Job job);

    boolean applyToJob(Long id, User user);
}
