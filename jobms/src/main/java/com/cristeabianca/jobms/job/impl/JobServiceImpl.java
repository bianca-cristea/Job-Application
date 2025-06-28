package com.cristeabianca.jobms.job.impl;

import com.cristeabianca.jobms.job.Job;
import com.cristeabianca.jobms.job.JobRepository;
import com.cristeabianca.jobms.job.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private final JobRepository jobRepository;


    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }



    @Override
    public List<Job> showAllJobs() {
        logger.debug("Fetching all jobs");
        return jobRepository.findAll();
    }

    @Override
    public boolean createNewJob(Job job) {
        if (job.getCompanyId() == null) return false;
        jobRepository.save(job);
        return true;
    }



    @Override
    public Job getJobById(Long id) {
        logger.debug("Fetching job by id: {}", id);
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
        logger.info("Deleting job with id: {}", id);
        try {
            jobRepository.deleteById(id);
            logger.info("Job with id {} deleted successfully", id);
            return true;
        } catch (Exception e) {
            logger.error("Failed to delete job with id {}: {}", id, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateJobById(Long id, Job updatedJob) {
        logger.info("Updating job with id: {}", id);
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setLocation(updatedJob.getLocation());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            jobRepository.save(job);
            logger.info("Job with id {} updated successfully", id);
            return true;
        } else {
            logger.warn("Job with id {} not found for update", id);
            return false;
        }
    }
}
