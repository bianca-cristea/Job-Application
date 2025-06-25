package com.cristeabianca.reviewms.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);
    List<Review> getAllReviews();
    Review getReview(Long companyId, Long reviewId);
    boolean addReview(Review review, Long companyId, Long userId);
    boolean updateReview(Long companyId, Long reviewId, Long userId, Review updatedReview);
    boolean deleteReview(Long companyId, Long reviewId, Long userId);
    Review getReviewById(Long reviewId);
}
