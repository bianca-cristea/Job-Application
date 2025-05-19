package com.cristeabianca.job_application.application.impl;

import com.cristeabianca.job_application.application.Application;
import com.cristeabianca.job_application.application.ApplicationRepository;
import com.cristeabianca.job_application.application.ApplicationService;
import com.cristeabianca.job_application.job.Job;
import com.cristeabianca.job_application.job.JobRepository;
import com.cristeabianca.job_application.user.User;
import com.cristeabianca.job_application.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  UserRepository userRepository,
                                  JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public List<Application> getApplicationsByUser(Long userId) {
        return applicationRepository.findByUserId(userId);
    }

    @Override
    public List<Application> getApplicationsByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    @Override
    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id).orElse(null);
    }

    @Override
    public boolean createApplication(Application application, Long userId, Long jobId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Job> jobOpt = jobRepository.findById(jobId);

        if (userOpt.isEmpty() || jobOpt.isEmpty()) {
            return false;
        }

        application.setUser(userOpt.get());
        application.setJob(jobOpt.get());
        application.setStatus(application.getStatus() != null ? application.getStatus() : "Pending");

        applicationRepository.save(application);
        return true;
    }

    @Override
    public boolean updateApplication(Long id, Application updatedApp) {
        Optional<Application> existingOpt = applicationRepository.findById(id);

        if (existingOpt.isEmpty()) {
            return false;
        }

        Application existingApp = existingOpt.get();
        existingApp.setStatus(updatedApp.getStatus());

        if (updatedApp.getUser() != null) {
            existingApp.setUser(updatedApp.getUser());
        }

        if (updatedApp.getJob() != null) {
            existingApp.setJob(updatedApp.getJob());
        }

        applicationRepository.save(existingApp);
        return true;
    }

    @Override
    public boolean deleteApplication(Long id) {
        if (!applicationRepository.existsById(id)) {
            return false;
        }

        applicationRepository.deleteById(id);
        return true;
    }
}
