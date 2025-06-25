package com.cristeabianca.job_application.review.impl;

import com.cristeabianca.job_application.company.Company;
import com.cristeabianca.job_application.company.CompanyService;
import com.cristeabianca.job_application.review.Review;
import com.cristeabianca.job_application.review.ReviewRepository;
import com.cristeabianca.job_application.review.ReviewService;
import com.cristeabianca.job_application.user.User;
import com.cristeabianca.job_application.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }
    @Override
    public Review getReview(Long companyId, Long reviewId) {
          return reviewRepository.findByCompanyIdAndId(companyId, reviewId)
                .orElse(null);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public boolean addReview(Review review, Long companyId, String username) {
        Company company = companyService.getCompanyById(companyId);
        User user = userRepository.findByUsername(username).orElse(null);

        if (company != null && user != null) {
            review.setCompany(company);
            review.setUser(user);
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
    public boolean updateReview(Long companyId, Long reviewId, String username, Review updatedReview) {
        // Găsește review-ul după companyId și reviewId
        Review existingReview = reviewRepository.findByCompanyIdAndId(companyId, reviewId).orElse(null);

        if (existingReview != null &&
                existingReview.getUser().getUsername().equals(username)) {

            existingReview.setTitle(updatedReview.getTitle());
            existingReview.setDescription(updatedReview.getDescription());
            existingReview.setRating(updatedReview.getRating());

            reviewRepository.save(existingReview);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId, String username) {
        Company company = companyService.getCompanyById(companyId);
        if (company == null) return false;

        Optional<Review> opt = reviewRepository.findById(reviewId);
        if (opt.isEmpty()) return false;

        Review rev = opt.get();
        if (!rev.getUser().getUsername().equals(username)) return false;

        reviewRepository.deleteById(reviewId);
        return true;
    }

}
