package com.cristeabianca.job_application.interview;

import com.cristeabianca.job_application.application.Application;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "interview")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scheduled_at")
    private LocalDate scheduledAt;

    private String location;

    @OneToOne
    @JoinColumn(name = "application_id")
    private Application application;

    public Interview() {
    }

    public Interview(Long id, LocalDate scheduledAt, String location, Application application) {
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

    public LocalDate getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDate scheduledAt) {
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
