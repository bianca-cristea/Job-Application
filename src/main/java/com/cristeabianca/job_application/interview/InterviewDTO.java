package com.cristeabianca.job_application.interview;

import java.time.LocalDateTime;

public class InterviewDTO {
    private Long id;
    private LocalDateTime scheduledAt;
    private String location;
    private String candidateName;
    private String companyName;

    public InterviewDTO(Long id, LocalDateTime scheduledAt, String location, String candidateName, String companyName) {
        this.id = id;
        this.scheduledAt = scheduledAt;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
