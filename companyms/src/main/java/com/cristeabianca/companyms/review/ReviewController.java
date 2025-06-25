package com.cristeabianca.companyms.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long companyId) {
        List<Review> reviews = reviewService.getAllReviews(companyId);
        return ResponseEntity.ok(reviews);
    }


    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long companyId, @PathVariable Long reviewId) {
        Review review = reviewService.getReview(companyId, reviewId);
        if (review == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(review);
    }


    @PostMapping
    public ResponseEntity<String> addReview(@PathVariable Long companyId,
                                            @RequestBody Review review,
                                            @RequestParam String username) {
        boolean created = reviewService.addReview(review, companyId, username);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Review created successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create review");
    }


    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long companyId,
                                               @PathVariable Long reviewId,
                                               @RequestParam String username,
                                               @RequestBody Review updatedReview) {
        boolean updated = reviewService.updateReview(companyId, reviewId, username, updatedReview);
        if (updated) {
            return ResponseEntity.ok("Review updated successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized to update this review");
    }


    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId,
                                               @PathVariable Long reviewId,
                                               @RequestParam String username) {
        boolean deleted = reviewService.deleteReview(companyId, reviewId, username);
        if (deleted) {
            return ResponseEntity.ok("Review deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized to delete this review");
    }
}
