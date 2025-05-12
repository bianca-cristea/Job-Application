package com.cristeabianca.job_application.job.impl;

import com.cristeabianca.job_application.job.Job;
import com.cristeabianca.job_application.job.JobService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private List<Job> jobs = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<Job> showAllJobs() {
        return jobs;
    }

    @Override
    public boolean createNewJob(Job job) {
        if (job != null) {
            job.setId(nextId++);
            jobs.add(job);
            return true;
        } else return false;

    }

    @Override
    public Job getJobById(Long id) {
        for (Job job : jobs) {
            if (job.getId().equals(id)) {
                return job;
            }
        }
        return null;
    }

    @Override
    public boolean deleteJobById(Long id) {
        return jobs.removeIf(job -> job.getId().equals(id));
    }

    @Override
    public boolean updateJobById(Long id,Job job){
        for(Job j:jobs){
            if(j.getId().equals(id)){
                j.setTitle(job.getTitle());
                j.setDescription(job.getDescription());
                j.setMinSalary(job.getMinSalary());
                j.setMaxSalary(job.getMaxSalary());
                j.setLocation(job.getLocation());
                return true;
            }
        }
        return false;
    }
}
