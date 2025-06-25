package com.cristeabianca.reviewms.review.impl;

import com.cristeabianca.reviewms.review.Review;
import com.cristeabianca.reviewms.review.ReviewRepository;
import com.cristeabianca.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate;

    private final String COMPANY_SERVICE_URL = "http://companyms/companies/";
    private final String USER_SERVICE_URL = "http://userms/users/";

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        return reviewRepository.findByCompanyIdAndId(companyId, reviewId).orElse(null);
    }

    @Override
    public boolean addReview(Review review, Long companyId, Long userId) {
         boolean companyExists;
        try {
            companyExists = restTemplate.getForObject(COMPANY_SERVICE_URL + companyId + "/exists", Boolean.class);
        } catch (Exception e) {
            companyExists = false;
        }

         boolean userExists;
        try {
            userExists = restTemplate.getForObject(USER_SERVICE_URL + userId + "/exists", Boolean.class);
        } catch (Exception e) {
            userExists = false;
        }

        if (companyExists && userExists) {
            review.setCompanyId(companyId);
            review.setUserId(userId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Long userId, Review updatedReview) {
        Optional<Review> existingReviewOpt = reviewRepository.findByCompanyIdAndId(companyId, reviewId);
        if (existingReviewOpt.isPresent()) {
            Review existingReview = existingReviewOpt.get();
            if (existingReview.getUserId().equals(userId)) {
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
    public boolean deleteReview(Long companyId, Long reviewId, Long userId) {
        Optional<Review> reviewOpt = reviewRepository.findByCompanyIdAndId(companyId, reviewId);
        if (reviewOpt.isPresent()) {
            Review review = reviewOpt.get();
            if (review.getUserId().equals(userId)) {
                reviewRepository.deleteById(reviewId);
                return true;
            }
        }
        return false;
    }
}
