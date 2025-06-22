package com.cristeabianca.job_application.interview;

import com.cristeabianca.job_application.application.Application;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "interview")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    private String location;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "application_id")
    private Application application;

    public Interview() {
    }

    public Interview(Long id, LocalDateTime scheduledAt, String location, Application application) {
        this.id = id;
        this.scheduledAt = scheduledAt;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
