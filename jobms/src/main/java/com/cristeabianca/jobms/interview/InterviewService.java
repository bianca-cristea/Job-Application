package com.cristeabianca.jobms.interview;

import com.cristeabianca.job_application.interview.Interview;
import com.cristeabianca.job_application.interview.InterviewDTO;

import java.util.List;
import java.util.Map;

public interface InterviewService {
    Interview getInterviewByApplication(Long applicationId);

    List<Interview> getInterviewsForUser(Long userId);
    List<Interview> getAllInterviews();
    List<Interview> getInterviewsByUsername(String username);
    boolean createInterview(Long applicationId, Interview interview);
    boolean updateInterview(Long id, Interview interview);
    List<InterviewDTO> getAllInterviewsDTO();

    boolean deleteInterview(Long id);
    Map<String, List<Interview>> getAllGroupedByCompany();

}
