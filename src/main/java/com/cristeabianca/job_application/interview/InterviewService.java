package com.cristeabianca.job_application.interview;

import java.util.List;

public interface InterviewService {
    Interview getInterviewByApplication(Long applicationId);

    List<Interview> getInterviewsForUser(Long userId);
    List<Interview> getAllInterviews();
    List<Interview> getInterviewsByUsername(String username);
    boolean createInterview(Long applicationId, Interview interview);
    boolean updateInterview(Long id, Interview interview);
    boolean deleteInterview(Long id);
}
