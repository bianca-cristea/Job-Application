package com.cristeabianca.jobms.interview.impl;


import com.cristeabianca.jobms.application.ApplicationClient;
import com.cristeabianca.jobms.application.ApplicationDTO;
import com.cristeabianca.jobms.interview.Interview;
import com.cristeabianca.jobms.interview.InterviewDTO;
import com.cristeabianca.jobms.interview.InterviewRepository;
import com.cristeabianca.jobms.interview.InterviewService;
import com.cristeabianca.jobms.job.Job;
import com.cristeabianca.jobms.job.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final JobRepository jobRepository;
    private final ApplicationClient applicationClient;

    public InterviewServiceImpl(
            InterviewRepository interviewRepository,
            JobRepository jobRepository,
            ApplicationClient applicationClient
    ) {
        this.interviewRepository = interviewRepository;
        this.jobRepository = jobRepository;
        this.applicationClient = applicationClient;
    }

    @Override
    public Map<String, List<Interview>> getAllGroupedByCompany() {
        List<Interview> all = interviewRepository.findAll();

        return all.stream()
                .collect(Collectors.groupingBy(i -> {
                    Job job = jobRepository.findById(i.getJobId().getId()).orElse(null);
                    if (job != null) {
                        return job.getCompanyId().toString();
                    }
                    return "Unknown";
                }));
    }

    @Override
    public Interview getInterviewByApplication(Long applicationId) {
        return interviewRepository.findByApplicationId(applicationId);
    }

    @Override
    public List<Interview> getInterviewsForUser(Long userId) {
        return interviewRepository.findAll().stream()
                .filter(i -> i.getUserId().equals(userId))
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
        ApplicationDTO app = applicationClient.getApplicationById(applicationId);
        if (app != null) {
            Job job = jobRepository.findById(app.getJobId()).orElse(null);
            if (job != null) {
                interview.setApplicationId(applicationId);
                interview.setUserId(app.getUserId());
                interview.setJobId(job);
                interviewRepository.save(interview);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateInterview(Long id, Interview updated) {
        Interview existing = interviewRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setJobId(updated.getJobId());
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

    @Override
    public List<InterviewDTO> getAllInterviewsDTO() {
        List<Interview> interviews = interviewRepository.findAll();
        return interviews.stream().map(interview -> new InterviewDTO(
                interview.getId(),
                interview.getScheduledAt(),
                interview.getJobId() != null ? interview.getJobId().toString() : null,
                interview.getUserId() != null ? interview.getUserId().toString() : null,
                null
        )).toList();
    }
}
