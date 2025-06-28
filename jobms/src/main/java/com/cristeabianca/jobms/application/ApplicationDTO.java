package com.cristeabianca.jobms.application;

public class ApplicationDTO {
    private Long id;
    private String status;
    private Long userId;
    private Long jobId;

    public ApplicationDTO() {}

    public ApplicationDTO(Long id, String status, Long userId, Long jobId) {
        this.id = id;
        this.status = status;
        this.userId = userId;
        this.jobId = jobId;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
