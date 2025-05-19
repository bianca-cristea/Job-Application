package com.cristeabianca.job_application.job.impl;

import com.cristeabianca.job_application.job.Job;
import com.cristeabianca.job_application.job.JobRepository;
import com.cristeabianca.job_application.job.JobService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

//  private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository){
        this.jobRepository=jobRepository;
    }


    @Override
    public List<Job> showAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public boolean createNewJob(Job job) {
        if (job != null) {
            jobRepository.save(job);
            return true;
        } else return false;

    }

    @Override
    public Job getJobById(Long id) {
         return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
         try{
             jobRepository.deleteById(id);
             return true;
         }
         catch (Exception e){
             return false;
         }
    }

    @Override
    public boolean updateJobById(Long id,Job updatedJob){
//Optional e un wrapper folosit pentru a reprezenta o valoare care poate exista sau nu.
//Daca nu e prezenta, returneaza Optional.empty(), mai bine decat null.
         Optional<Job> jobOptional = jobRepository.findById(id);
         if(jobOptional.isPresent()){
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setLocation(updatedJob.getLocation());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            jobRepository.save(job);

            return true;
         }
         return false;
    }
}
