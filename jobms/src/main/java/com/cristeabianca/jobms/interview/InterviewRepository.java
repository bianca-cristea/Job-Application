package com.cristeabianca.jobms.interview;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByApplicationUserUsername(String username);
    Interview findByApplicationId(Long applicationId);

}
