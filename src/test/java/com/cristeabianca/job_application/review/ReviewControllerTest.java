package com.cristeabianca.job_application.review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private Review review;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        review = new Review();
        review.setId(1L);
        review.setTitle("Good company");
        review.setDescription("Nice place to work");
        review.setRating(4.7);
    }

    @Test
    void getAllReviews_ReturnsReviews() {
        when(reviewService.getAllReviews(1L)).thenReturn(List.of(review));

        ResponseEntity<List<Review>> response = reviewController.getAllReviews(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(reviewService, times(1)).getAllReviews(1L);
    }

    @Test
    void addReview_ReturnsOk_WhenSuccess() {
        when(reviewService.addReview(any(Review.class), eq(1L), eq("testuser"))).thenReturn(true);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        ResponseEntity<String> response = reviewController.addReview(1L, review, userDetails);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Review added successfully", response.getBody());
    }

    @Test
    void addReview_ReturnsNotFound_WhenFail() {
        when(reviewService.addReview(any(Review.class), eq(1L), eq("testuser"))).thenReturn(false);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        ResponseEntity<String> response = reviewController.addReview(1L, review, userDetails);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Review could not be added.", response.getBody());
    }

    @Test
    void getReview_ReturnsReview() {
        when(reviewService.getReview(1L, 1L)).thenReturn(review);

        ResponseEntity<Review> response = reviewController.getReview(1L, 1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(review, response.getBody());
    }

    @Test
    void updateReview_ReturnsOk_WhenSuccess() {
        when(reviewService.updateReview(1L, 1L, "testuser", review)).thenReturn(true);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        ResponseEntity<String> response = reviewController.updateReview(1L, 1L, review, userDetails);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Review updated successfully", response.getBody());
    }

    @Test
    void updateReview_ReturnsNotFound_WhenFail() {
        when(reviewService.updateReview(1L, 1L, "testuser", review)).thenReturn(false);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        ResponseEntity<String> response = reviewController.updateReview(1L, 1L, review, userDetails);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Review could not be updated.", response.getBody());
    }

    @Test
    void deleteReview_ReturnsOk_WhenSuccess() {
        when(reviewService.deleteReview(1L, 1L, "testuser")).thenReturn(true);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        ResponseEntity<String> response = reviewController.deleteReview(1L, 1L, userDetails);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Review deleted successfully", response.getBody());
    }

    @Test
    void deleteReview_ReturnsNotFound_WhenFail() {
        when(reviewService.deleteReview(1L, 1L, "testuser")).thenReturn(false);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        ResponseEntity<String> response = reviewController.deleteReview(1L, 1L, userDetails);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Review could not be deleted.", response.getBody());
    }
}
