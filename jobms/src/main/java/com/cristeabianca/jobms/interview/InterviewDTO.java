package com.cristeabianca.jobms.interview;

import java.time.LocalDateTime;
public class InterviewDTO {
    private Long id;
    private LocalDateTime scheduledAt;
    private String jobTitle;
    private String candidateName;
    private String companyName;

    public InterviewDTO(Long id, LocalDateTime scheduledAt, String jobTitle, String candidateName, String companyName) {
        this.id = id;
        this.scheduledAt = scheduledAt;
        this.jobTitle = jobTitle;
        this.candidateName = candidateName;
        this.companyName = companyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
