package com.cristeabianca.jobms.application.impl;

import com.cristeabianca.jobms.application.Application;
import com.cristeabianca.jobms.application.ApplicationRepository;
import com.cristeabianca.jobms.application.ApplicationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
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
        if (userId == null || jobId == null) {
            return false;
        }

        application.setUserId(userId);
        application.setJobId(jobId);
        if (application.getStatus() == null) {
            application.setStatus("Pending");
        }
        application.setCreatedAt(LocalDateTime.now());

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

        if (updatedApp.getStatus() != null) {
            existingApp.setStatus(updatedApp.getStatus());
        }
        if (updatedApp.getUserId() != null) {
            existingApp.setUserId(updatedApp.getUserId());
        }
        if (updatedApp.getJobId() != null) {
            existingApp.setJobId(updatedApp.getJobId());
        }
        if (updatedApp.getInterviewId() != null) {
            existingApp.setInterviewId(updatedApp.getInterviewId());
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
