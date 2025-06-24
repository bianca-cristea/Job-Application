package com.cristeabianca.job_application.review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllReviews() {
        Long companyId = 1L;
        Review review = new Review();
        when(reviewService.getAllReviews(companyId)).thenReturn(List.of(review));

        ResponseEntity<List<Review>> response = reviewController.getAllReviews(companyId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(reviewService).getAllReviews(companyId);
    }

    @Test
    void testGetAllReviewsAdmin_WithAdminRole() {
        doReturn(Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .when(userDetails)
                .getAuthorities();

        Review review = new Review();
        when(reviewService.getAllReviews()).thenReturn(List.of(review));

        ResponseEntity<List<Review>> response = reviewController.getAllReviewsAdmin(userDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(reviewService).getAllReviews();
    }

    @Test
    void testGetAllReviewsAdmin_WithoutAdminRole() {
        doReturn(new HashSet<>(Set.of(new SimpleGrantedAuthority("ROLE_USER"))))
                .when(userDetails)
                .getAuthorities();
        ResponseEntity<List<Review>> response = reviewController.getAllReviewsAdmin(userDetails);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verify(reviewService, never()).getAllReviews();
    }

    @Test
    void testAddReview_Success() {
        Long companyId = 1L;
        Review review = new Review();
        when(userDetails.getUsername()).thenReturn("user");
        when(reviewService.addReview(review, companyId, "user")).thenReturn(true);

        ResponseEntity<String> response = reviewController.addReview(companyId, review, userDetails);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Review added successfully", response.getBody());
        verify(reviewService).addReview(review, companyId, "user");
    }

    @Test
    void testAddReview_NotAuthenticated() {
        Long companyId = 1L;
        Review review = new Review();

        ResponseEntity<String> response = reviewController.addReview(companyId, review, null);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User not authenticated", response.getBody());
        verify(reviewService, never()).addReview(any(), anyLong(), anyString());
    }

    @Test
    void testAddReview_Failure() {
        Long companyId = 1L;
        Review review = new Review();
        when(userDetails.getUsername()).thenReturn("user");
        when(reviewService.addReview(review, companyId, "user")).thenReturn(false);

        ResponseEntity<String> response = reviewController.addReview(companyId, review, userDetails);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Review could not be added", response.getBody());
        verify(reviewService).addReview(review, companyId, "user");
    }

    @Test
    void testGetReview_Found() {
        Long companyId = 1L;
        Long reviewId = 2L;
        Review review = new Review();
        when(reviewService.getReview(companyId, reviewId)).thenReturn(review);

        ResponseEntity<Review> response = reviewController.getReview(companyId, reviewId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(review, response.getBody());
    }

    @Test
    void testGetReview_NotFound() {
        Long companyId = 1L;
        Long reviewId = 2L;
        when(reviewService.getReview(companyId, reviewId)).thenReturn(null);

        ResponseEntity<Review> response = reviewController.getReview(companyId, reviewId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateReview_Success() {
        Long companyId = 1L;
        Long reviewId = 2L;
        Review updatedReview = new Review();
        when(userDetails.getUsername()).thenReturn("user");
        when(reviewService.updateReview(companyId, reviewId, "user", updatedReview)).thenReturn(true);

        ResponseEntity<String> response = reviewController.updateReview(companyId, reviewId, updatedReview, userDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Review updated successfully", response.getBody());
    }

    @Test
    void testUpdateReview_NotAuthenticated() {
        Long companyId = 1L;
        Long reviewId = 2L;
        Review updatedReview = new Review();

        ResponseEntity<String> response = reviewController.updateReview(companyId, reviewId, updatedReview, null);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User not authenticated", response.getBody());
    }

    @Test
    void testUpdateReview_Failure() {
        Long companyId = 1L;
        Long reviewId = 2L;
        Review updatedReview = new Review();
        when(userDetails.getUsername()).thenReturn("user");
        when(reviewService.updateReview(companyId, reviewId, "user", updatedReview)).thenReturn(false);

        ResponseEntity<String> response = reviewController.updateReview(companyId, reviewId, updatedReview, userDetails);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Review could not be updated", response.getBody());
    }

    @Test
    void testDeleteReview_Success() {
        Long companyId = 1L;
        Long reviewId = 2L;
        when(userDetails.getUsername()).thenReturn("user");
        when(reviewService.deleteReview(companyId, reviewId, "user")).thenReturn(true);

        ResponseEntity<String> response = reviewController.deleteReview(companyId, reviewId, userDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Review deleted successfully", response.getBody());
    }

    @Test
    void testDeleteReview_NotAuthenticated() {
        Long companyId = 1L;
        Long reviewId = 2L;

        ResponseEntity<String> response = reviewController.deleteReview(companyId, reviewId, null);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User not authenticated", response.getBody());
    }

    @Test
    void testDeleteReview_Failure() {
        Long companyId = 1L;
        Long reviewId = 2L;
        when(userDetails.getUsername()).thenReturn("user");
        when(reviewService.deleteReview(companyId, reviewId, "user")).thenReturn(false);

        ResponseEntity<String> response = reviewController.deleteReview(companyId, reviewId, userDetails);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Review could not be deleted", response.getBody());
    }
}
