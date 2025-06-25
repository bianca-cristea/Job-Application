package com.cristeabianca.companyms.review;

import com.cristeabianca.job_application.review.Review;
import com.cristeabianca.job_application.review.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{companyId}/reviews")
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long companyId) {
        List<Review> reviews = reviewService.getAllReviews(companyId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<Review>> getAllReviewsAdmin(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null || userDetails.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/{companyId}/reviews")
    public ResponseEntity<String> addReview(@PathVariable Long companyId,
                                            @RequestBody Review review,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        boolean success = reviewService.addReview(review, companyId, userDetails.getUsername());
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Review added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Review could not be added");
        }
    }

    @GetMapping("/{companyId}/reviews/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long companyId,
                                            @PathVariable Long reviewId) {
        Review review = reviewService.getReview(companyId, reviewId);
        if (review != null) {
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{companyId}/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(
            @PathVariable Long companyId,
            @PathVariable Long reviewId,
            @RequestBody Review updatedReview,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }


        boolean success = reviewService.updateReview(companyId, reviewId, userDetails.getUsername(), updatedReview);
        if (success) {
            return ResponseEntity.ok("Review updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review could not be updated");
        }
    }

    @DeleteMapping("/{companyId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId,
                                               @PathVariable Long reviewId,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        boolean success = reviewService.deleteReview(companyId, reviewId, userDetails.getUsername());  // aici am adăugat companyId
        if (success) {
            return ResponseEntity.ok("Review deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review could not be deleted");
        }
    }

}
