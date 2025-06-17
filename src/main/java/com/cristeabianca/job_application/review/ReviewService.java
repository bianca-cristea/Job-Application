package com.cristeabianca.job_application.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews(Long companyId);
    List<Review> getAllReviews();
    boolean addReview(Review review, Long companyId, String username);
    Review getReview(Long reviewId, Long companyId);
    boolean updateReview(Long reviewId, Long companyId, String username, Review updatedReview);

    boolean deleteReview(Long reviewId, Long companyId, String username);

}
