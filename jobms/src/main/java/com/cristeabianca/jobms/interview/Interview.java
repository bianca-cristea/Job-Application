package com.cristeabianca.jobms.interview;

import com.cristeabianca.jobms.job.Job;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime scheduledAt;

    private Long userId;
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job jobId;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public Job getJobId() { return jobId; }
    public void setJobId(Job jobId) { this.jobId = jobId; }
}
