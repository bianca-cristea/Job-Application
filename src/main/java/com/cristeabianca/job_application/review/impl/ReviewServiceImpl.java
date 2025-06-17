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

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService,UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
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
    public Review getReview(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews.stream().filter(review -> review.getId().equals(reviewId)).findFirst().orElse(null);
    }

    @Override
    public boolean updateReview(Long reviewId, Long companyId, String username, Review updatedReview) {
        Company company = companyService.getCompanyById(companyId);
        Review existingReview = reviewRepository.findById(reviewId).orElse(null);

        if (company != null && existingReview != null &&
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
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    @Override
    public boolean deleteReview(Long reviewId, Long companyId, String username) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        Company company = companyService.getCompanyById(companyId);

        if (company != null && review != null &&
                review.getCompany().getId().equals(companyId) &&
                review.getUser().getUsername().equals(username)) {

            review.setCompany(null);
            reviewRepository.deleteById(reviewId);
            return true;
        }

        return false;
    }

}
