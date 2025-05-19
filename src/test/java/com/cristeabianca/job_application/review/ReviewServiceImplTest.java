package com.cristeabianca.job_application.review.impl;

import com.cristeabianca.job_application.company.Company;
import com.cristeabianca.job_application.company.CompanyService;
import com.cristeabianca.job_application.review.Review;
import com.cristeabianca.job_application.review.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Company company;
    private Review review;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        company = new Company();
        company.setId(1L);

        review = new Review();
        review.setId(1L);
        review.setTitle("Great place");
        review.setCompany(company);
        review.setRating(4.5);
    }

    @Test
    void getAllReviews_ReturnsList() {
        when(reviewRepository.findByCompanyId(1L)).thenReturn(List.of(review));

        List<Review> reviews = reviewService.getAllReviews(1L);
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        verify(reviewRepository, times(1)).findByCompanyId(1L);
    }

    @Test
    void addReview_WithExistingCompany_ReturnsTrue() {
        when(companyService.getCompanyById(1L)).thenReturn(company);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        boolean result = reviewService.addReview(review, 1L);
        assertTrue(result);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void addReview_WithNonExistingCompany_ReturnsFalse() {
        when(companyService.getCompanyById(1L)).thenReturn(null);

        boolean result = reviewService.addReview(review, 1L);
        assertFalse(result);
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void getReview_WhenExists_ReturnsReview() {
        when(reviewRepository.findByCompanyId(1L)).thenReturn(List.of(review));

        Review found = reviewService.getReview(1L, 1L);
        assertNotNull(found);
        assertEquals(review.getId(), found.getId());
    }

    @Test
    void getReview_WhenNotExists_ReturnsNull() {
        when(reviewRepository.findByCompanyId(1L)).thenReturn(Collections.emptyList());

        Review found = reviewService.getReview(1L, 1L);
        assertNull(found);
    }

    @Test
    void updateReview_WithValidCompany_ReturnsTrue() {
        when(companyService.getCompanyById(1L)).thenReturn(company);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        boolean updated = reviewService.updateReview(1L, 1L, review);
        assertTrue(updated);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void updateReview_WithInvalidCompany_ReturnsFalse() {
        when(companyService.getCompanyById(1L)).thenReturn(null);

        boolean updated = reviewService.updateReview(1L, 1L, review);
        assertFalse(updated);
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void deleteReview_WithValidCompanyAndReview_ReturnsTrue() {
        when(companyService.getCompanyById(1L)).thenReturn(company);
        when(reviewRepository.existsById(1L)).thenReturn(true);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        // mock updateCompany to always return true
        when(companyService.updateCompany(1L, company)).thenReturn(true);

        boolean deleted = reviewService.deleteReview(1L, 1L);
        assertTrue(deleted);
        verify(reviewRepository, times(1)).deleteById(1L);
        verify(companyService, times(1)).updateCompany(1L, company);
    }

    @Test
    void deleteReview_WithInvalidCompany_ReturnsFalse() {
        when(companyService.getCompanyById(1L)).thenReturn(null);

        boolean deleted = reviewService.deleteReview(1L, 1L);
        assertFalse(deleted);
        verify(reviewRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteReview_WithNonExistingReview_ReturnsFalse() {
        when(companyService.getCompanyById(1L)).thenReturn(company);
        when(reviewRepository.existsById(1L)).thenReturn(false);

        boolean deleted = reviewService.deleteReview(1L, 1L);
        assertFalse(deleted);
        verify(reviewRepository, never()).deleteById(anyLong());
    }
}
