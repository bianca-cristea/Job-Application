package com.cristeabianca.reviewms.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
        List<Review> reviews = reviewService.getAllReviews(companyId);
        return ResponseEntity.ok(reviews);
    }

     @GetMapping("/admin/all")
    public ResponseEntity<List<Review>> getAllReviewsAdmin(@RequestParam String adminToken) {
        if (!"admin123".equals(adminToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }


    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId,
                                            @RequestParam Long userId,
                                            @RequestBody Review review) {
        boolean success = reviewService.addReview(review, companyId, userId);
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
        return (review != null) ? ResponseEntity.ok(review) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PutMapping("/{companyId}/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long companyId,
                                               @PathVariable Long reviewId,
                                               @RequestParam Long userId,
                                               @RequestBody Review updatedReview) {
        boolean success = reviewService.updateReview(companyId, reviewId, userId, updatedReview);
        return success ? ResponseEntity.ok("Review updated successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review could not be updated");
    }


    @DeleteMapping("/{companyId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId,
                                               @PathVariable Long reviewId,
                                               @RequestParam Long userId) {
        boolean success = reviewService.deleteReview(companyId, reviewId, userId);
        return success ? ResponseEntity.ok("Review deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review could not be deleted");
    }
}
