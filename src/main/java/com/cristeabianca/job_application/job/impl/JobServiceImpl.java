package com.cristeabianca.job_application.job.impl;

import com.cristeabianca.job_application.application.Application;
import com.cristeabianca.job_application.application.ApplicationRepository;
import com.cristeabianca.job_application.company.Company;
import com.cristeabianca.job_application.company.CompanyRepository;
import com.cristeabianca.job_application.job.Job;
import com.cristeabianca.job_application.job.JobRepository;
import com.cristeabianca.job_application.job.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private final JobRepository jobRepository;

    private final ApplicationRepository applicationRepository;

    private final CompanyRepository companyRepository;

    public JobServiceImpl(JobRepository jobRepository,
                          ApplicationRepository applicationRepository,
                          CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.companyRepository = companyRepository;
    }

    public boolean applyToJob(Long jobId, Application application) {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job == null) return false;

        application.setJob(job);
        applicationRepository.save(application);
        return true;
    }


    @Override
    public List<Job> showAllJobs() {
        logger.debug("Fetching all jobs");
        return jobRepository.findAll();
    }

    @Override
    public boolean createNewJob(Job job) {
        logger.info("Creating new job with title: {}", job.getTitle());
        if (job != null && job.getCompany() != null) {
            Long companyId = job.getCompany().getId();
            Company company = companyRepository.findById(companyId).orElse(null);
            if (company == null) {
                logger.warn("Invalid company ID: {}", companyId);
                return false;
            }
            job.setCompany(company);
            jobRepository.save(job);
            logger.info("Job '{}' created successfully with company '{}'", job.getTitle(), company.getName());
            return true;
        } else {
            logger.warn("Attempted to create job with null company");
            return false;
        }
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
