package com.cristeabianca.job_application.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews(Long companyId);

    List<Review> getAllReviews();

    boolean addReview(Review review, Long companyId, String username);

    Review getReviewById(Long reviewId);

    boolean updateReview(Long companyId, Long reviewId, String username, Review updatedReview);

    boolean deleteReview(Long reviewId, String username);

    Review getReview(Long companyId, Long reviewId);
}
