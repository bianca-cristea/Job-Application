package com.cristeabianca.job_application.interview;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    Interview findByApplicationId(Long applicationId);
}
