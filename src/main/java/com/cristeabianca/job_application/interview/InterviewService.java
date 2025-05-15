package com.cristeabianca.job_application.interview;

import java.util.List;

public interface InterviewService {
    Interview getInterviewByApplication(Long applicationId);
    boolean createInterview(Long applicationId, Interview interview);
    boolean updateInterview(Long id, Interview interview);
    boolean deleteInterview(Long id);
}
