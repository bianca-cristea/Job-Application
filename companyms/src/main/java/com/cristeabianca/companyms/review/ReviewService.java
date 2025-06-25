package com.cristeabianca.companyms.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews(Long companyId);

    Review getReview(Long companyId, Long reviewId);

    List<Review> getAllReviews();

    boolean addReview(Review review);

    boolean updateReview(Long companyId, Long reviewId, String username, Review updatedReview);

    boolean deleteReview(Long companyId, Long reviewId, String username);
}
