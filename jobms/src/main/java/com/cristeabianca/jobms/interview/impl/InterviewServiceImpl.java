package com.cristeabianca.jobms.interview.impl;

import com.cristeabianca.job_application.application.Application;
import com.cristeabianca.job_application.application.ApplicationRepository;
import com.cristeabianca.job_application.job.Job;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;

    public InterviewServiceImpl(InterviewRepository interviewRepository, ApplicationRepository applicationRepository) {
        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Map<String, List<Interview>> getAllGroupedByCompany() {
        List<Interview> all = interviewRepository.findAll();
        return all.stream().collect(Collectors.groupingBy(i -> i.getApplication().getJob().getCompany().getName()));
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

            Job job = app.getJob();
            interview.setJob(job);

            interviewRepository.save(interview);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateInterview(Long id, Interview updated) {
        Interview existing = interviewRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setJob(updated.getJob());
            existing.setScheduledAt(updated.getScheduledAt());
            interviewRepository.save(existing);
            return true;
        }
        return false;
    }


    @Override
    public boolean deleteInterview(Long id) {
        Optional<Interview> interviewOpt = interviewRepository.findById(id);
        if (interviewOpt.isPresent()) {
            Interview interview = interviewOpt.get();
            Application application = interview.getApplication();
            if (application != null) {
                application.setInterview(null);
            }
            interviewRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public List<InterviewDTO> getAllInterviewsDTO() {
        List<Interview> interviews = interviewRepository.findAll();
        return interviews.stream().map(interview -> {
            String candidateName = null;
            String companyName = null;
            String jobTitle = null;

            if (interview.getApplication() != null) {
                if (interview.getApplication().getUser() != null) {
                    candidateName = interview.getApplication().getUser().getUsername();
                }
                if (interview.getApplication().getJob() != null) {
                    jobTitle = interview.getApplication().getJob().getTitle();
                    if (interview.getApplication().getJob().getCompany() != null) {
                        companyName = interview.getApplication().getJob().getCompany().getName();
                    }
                }
            }

            return new InterviewDTO(
                    interview.getId(),
                    interview.getScheduledAt(),
                    jobTitle,
                    candidateName,
                    companyName
            );
        }).toList();
    }

}
