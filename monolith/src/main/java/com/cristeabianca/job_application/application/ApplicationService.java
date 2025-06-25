package com.cristeabianca.job_application.application;

import java.util.List;

public interface ApplicationService {
    List<Application> getAllApplications();
    List<Application> getApplicationsByUser(Long userId);
    List<Application> getApplicationsByJob(Long jobId);
    Application getApplicationById(Long id);
    boolean createApplication(Application application, Long userId, Long jobId);
    boolean updateApplication(Long id, Application application);
    boolean deleteApplication(Long id);
}
