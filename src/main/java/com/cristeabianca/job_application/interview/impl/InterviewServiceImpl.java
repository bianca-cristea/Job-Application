package com.cristeabianca.job_application.interview.impl;

import com.cristeabianca.job_application.application.Application;
import com.cristeabianca.job_application.application.ApplicationRepository;
import com.cristeabianca.job_application.interview.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;

    public InterviewServiceImpl(InterviewRepository interviewRepository, ApplicationRepository applicationRepository) {
        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Interview getInterviewByApplication(Long applicationId) {
        return interviewRepository.findByApplicationId(applicationId);
    }

    @Override
    public List<Interview> getInterviewsForUser(Long userId) {
        return interviewRepository.findAll().stream()
                .filter(i -> i.getApplication().getUser().getId().equals(userId))
                .toList();
    }

    @Override
    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    @Override
    public List<Interview> getInterviewsByUsername(String username) {
        return interviewRepository.findByApplicationUserUsername(username);
    }


    @Override
    public boolean createInterview(Long applicationId, Interview interview) {
        Application app = applicationRepository.findById(applicationId).orElse(null);
        if (app != null) {
            interview.setApplication(app);
            interviewRepository.save(interview);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateInterview(Long id, Interview updated) {
        Interview existing = interviewRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setLocation(updated.getLocation());
            existing.setScheduledAt(updated.getScheduledAt());
            interviewRepository.save(existing);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteInterview(Long id) {
        if (interviewRepository.existsById(id)) {
            interviewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
