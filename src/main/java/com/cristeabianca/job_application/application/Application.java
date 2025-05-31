package com.cristeabianca.job_application.application;

import com.cristeabianca.job_application.job.Job;
import com.cristeabianca.job_application.user.User;
import com.cristeabianca.job_application.interview.Interview;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @JsonIgnore
    @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
    private Interview interview;

    public Application() {
    }

    public Application(Long id, String status, User user, Job job, Interview interview) {
        this.id = id;
        this.status = status;
        this.user = user;
        this.job = job;
        this.interview = interview;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }
}
