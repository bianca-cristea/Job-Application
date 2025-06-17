package com.cristeabianca.job_application.job;

import com.cristeabianca.job_application.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Long> {

}
