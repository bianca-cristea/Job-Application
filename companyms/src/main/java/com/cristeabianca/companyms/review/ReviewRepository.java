package com.cristeabianca.companyms.review;

import com.cristeabianca.job_application.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCompanyId(Long companyId);
    Optional<Review> findByCompanyIdAndId(Long companyId, Long reviewId);
}
