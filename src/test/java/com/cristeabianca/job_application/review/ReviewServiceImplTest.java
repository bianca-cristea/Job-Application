package com.cristeabianca.job_application.review;

import com.cristeabianca.job_application.company.Company;
import com.cristeabianca.job_application.company.CompanyService;
import com.cristeabianca.job_application.review.Review;
import com.cristeabianca.job_application.review.ReviewRepository;
import com.cristeabianca.job_application.review.impl.ReviewServiceImpl;
import com.cristeabianca.job_application.user.User;
import com.cristeabianca.job_application.user.UserRepository;
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

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Company company;
    private Review review;
    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        company = new Company();
        company.setId(1L);

        user = new User();
        user.setUsername("testuser");

        review = new Review();
        review.setId(1L);
        review.setTitle("Great place");
        review.setCompany(company);
        review.setUser(user);
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
    void addReview_WithExistingCompanyAndUser_ReturnsTrue() {
        when(companyService.getCompanyById(1L)).thenReturn(company);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        boolean result = reviewService.addReview(review, 1L, "testuser");
        assertTrue(result);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void addReview_WithNonExistingCompanyOrUser_ReturnsFalse() {
        when(companyService.getCompanyById(1L)).thenReturn(null);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        boolean result = reviewService.addReview(review, 1L, "testuser");
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
    void updateReview_WithValidCompanyAndUser_ReturnsTrue() {
        when(companyService.getCompanyById(1L)).thenReturn(company);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review updatedReview = new Review();
        updatedReview.setTitle("Updated title");
        updatedReview.setDescription("Updated description");
        updatedReview.setRating(5.0);

        boolean updated = reviewService.updateReview(1L, 1L, "testuser", updatedReview);
        assertTrue(updated);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void updateReview_WithInvalidCompanyOrUser_ReturnsFalse() {
        when(companyService.getCompanyById(1L)).thenReturn(null);
        boolean updatedNullCompany = reviewService.updateReview(1L, 1L, "testuser", review);
        assertFalse(updatedNullCompany);

        when(companyService.getCompanyById(1L)).thenReturn(company);
        Review otherUserReview = new Review();
        otherUserReview.setUser(new User());
        otherUserReview.getUser().setUsername("otheruser");
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(otherUserReview));

        boolean updatedWrongUser = reviewService.updateReview(1L, 1L, "testuser", review);
        assertFalse(updatedWrongUser);
    }

    @Test
    void getAllReviews_WithoutParams_ReturnsAll() {
        when(reviewRepository.findAll()).thenReturn(List.of(review));

        List<Review> allReviews = reviewService.getAllReviews();
        assertNotNull(allReviews);
        assertEquals(1, allReviews.size());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void deleteReview_WithValidCompanyAndUser_ReturnsTrue() {
        when(companyService.getCompanyById(1L)).thenReturn(company);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        boolean deleted = reviewService.deleteReview(1L, 1L, "testuser");
        assertTrue(deleted);
        verify(reviewRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteReview_WithInvalidCompanyOrUser_ReturnsFalse() {
        when(companyService.getCompanyById(1L)).thenReturn(null);
        boolean deletedNullCompany = reviewService.deleteReview(1L, 1L, "testuser");
        assertFalse(deletedNullCompany);

        when(companyService.getCompanyById(1L)).thenReturn(company);
        Review otherUserReview = new Review();
        otherUserReview.setUser(new User());
        otherUserReview.getUser().setUsername("otheruser");
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(otherUserReview));

        boolean deletedWrongUser = reviewService.deleteReview(1L, 1L, "testuser");
        assertFalse(deletedWrongUser);
    }
}
