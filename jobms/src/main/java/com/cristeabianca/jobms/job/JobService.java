package com.cristeabianca.jobms.job;


import java.util.List;

public interface JobService {
    List<Job> showAllJobs();
    boolean createNewJob(Job job);
    Job getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean updateJobById(Long id, Job job);


}
