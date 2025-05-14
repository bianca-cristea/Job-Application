package com.cristeabianca.job_application.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews(Long companyId);
    boolean addReview(Review review, Long companyId);
    Review getReview(Long reviewId, Long companyId);
    boolean updateReview(Long companyId,Long reviewId, Review updatedReview);
    boolean deleteReview(Long companyId,Long reviewId);
}
