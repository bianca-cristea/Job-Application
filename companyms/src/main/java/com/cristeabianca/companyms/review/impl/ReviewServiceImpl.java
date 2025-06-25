package com.cristeabianca.companyms.review.impl;

import com.cristeabianca.companyms.review.Review;
import com.cristeabianca.companyms.review.ReviewRepository;
import com.cristeabianca.companyms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        return reviewRepository.findByCompanyIdAndId(companyId, reviewId).orElse(null);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public boolean addReview(Review review) {
         if (review.getCompanyId() != null && review.getUsername() != null) {
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, String username, Review updatedReview) {
        Optional<Review> optReview = reviewRepository.findByCompanyIdAndId(companyId, reviewId);

        if (optReview.isPresent()) {
            Review existingReview = optReview.get();

            if (existingReview.getUsername().equals(username)) {
                existingReview.setTitle(updatedReview.getTitle());
                existingReview.setDescription(updatedReview.getDescription());
                existingReview.setRating(updatedReview.getRating());

                reviewRepository.save(existingReview);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId, String username) {
        Optional<Review> optReview = reviewRepository.findByCompanyIdAndId(companyId, reviewId);

        if (optReview.isPresent()) {
            Review review = optReview.get();

            if (review.getUsername().equals(username)) {
                reviewRepository.delete(review);
                return true;
            }
        }
        return false;
    }
}
