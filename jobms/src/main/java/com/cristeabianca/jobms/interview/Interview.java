package com.cristeabianca.jobms.interview;

import com.cristeabianca.job_application.application.Application;
import com.cristeabianca.job_application.job.Job;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "interview")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "scheduled_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime scheduledAt;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private Job job;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "application_id")
    private Application application;

    public Interview() {
    }

    public Interview(Long id, LocalDateTime scheduledAt, Job job, Application application) {
        this.id = id;
        this.scheduledAt = scheduledAt;
        this.job = job;
        this.application = application;
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

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
    public void setJobTitle(String jobTitle) {
        this.job.setTitle(jobTitle);
    }
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
