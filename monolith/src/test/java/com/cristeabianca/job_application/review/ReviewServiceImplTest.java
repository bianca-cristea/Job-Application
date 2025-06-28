package com.cristeabianca.job_application.review;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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

class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CompanyService companyService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReviewSuccess() {
        Review review = new Review();
        Long companyId = 1L;
        String username = "user1";
        Company company = new Company();
        company.setId(companyId);
        User user = new User();
        user.setUsername(username);

        when(companyService.getCompanyById(companyId)).thenReturn(company);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        boolean result = reviewService.addReview(review, companyId, username);

        assertTrue(result);
        assertEquals(company, review.getCompany());
        assertEquals(user, review.getUser());
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void testAddReviewFailWhenCompanyNull() {
        Review review = new Review();
        Long companyId = 1L;
        String username = "user1";

        when(companyService.getCompanyById(companyId)).thenReturn(null);

        boolean result = reviewService.addReview(review, companyId, username);
        assertFalse(result);
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void testGetAllReviews() {
        Long companyId = 1L;
        List<Review> reviews = List.of(new Review(), new Review());

        when(reviewRepository.findByCompanyId(companyId)).thenReturn(reviews);

        List<Review> result = reviewService.getAllReviews(companyId);
        assertEquals(reviews, result);
    }

    @Test
    void testUpdateReviewSuccess() {
        Long companyId = 1L;
        Long reviewId = 2L;
        String username = "user1";

        User user = new User();
        user.setUsername(username);

        Review existingReview = new Review();
        existingReview.setUser(user);
        existingReview.setTitle("Old");
        existingReview.setDescription("Old desc");
        existingReview.setRating(3);

        Review updatedReview = new Review();
        updatedReview.setTitle("New");
        updatedReview.setDescription("New desc");
        updatedReview.setRating(5);

        when(reviewRepository.findByCompanyIdAndId(companyId, reviewId)).thenReturn(Optional.of(existingReview));

        boolean result = reviewService.updateReview(companyId, reviewId, username, updatedReview);

        assertTrue(result);
        assertEquals("New", existingReview.getTitle());
        assertEquals("New desc", existingReview.getDescription());
        assertEquals(5, existingReview.getRating());
        verify(reviewRepository, times(1)).save(existingReview);
    }

    @Test
    void testUpdateReviewFailWhenUserMismatch() {
        Long companyId = 1L;
        Long reviewId = 2L;
        String username = "user1";

        User user = new User();
        user.setUsername("otherUser");

        Review existingReview = new Review();
        existingReview.setUser(user);

        Review updatedReview = new Review();

        when(reviewRepository.findByCompanyIdAndId(companyId, reviewId)).thenReturn(Optional.of(existingReview));

        boolean result = reviewService.updateReview(companyId, reviewId, username, updatedReview);
        assertFalse(result);
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void testDeleteReviewSuccess() {
        Long companyId = 1L;
        Long reviewId = 2L;
        String username = "user1";

        Company company = new Company();
        company.setId(companyId);

        User user = new User();
        user.setUsername(username);

        Review review = new Review();
        review.setUser(user);

        when(companyService.getCompanyById(companyId)).thenReturn(company);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        boolean result = reviewService.deleteReview(companyId, reviewId, username);
        assertTrue(result);
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    void testDeleteReviewFailWhenCompanyNull() {
        Long companyId = 1L;
        Long reviewId = 2L;
        String username = "user1";

        when(companyService.getCompanyById(companyId)).thenReturn(null);

        boolean result = reviewService.deleteReview(companyId, reviewId, username);
        assertFalse(result);
        verify(reviewRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteReviewFailWhenReviewNotFound() {
        Long companyId = 1L;
        Long reviewId = 2L;
        String username = "user1";

        Company company = new Company();
        when(companyService.getCompanyById(companyId)).thenReturn(company);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        boolean result = reviewService.deleteReview(companyId, reviewId, username);
        assertFalse(result);
        verify(reviewRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteReviewFailWhenUserMismatch() {
        Long companyId = 1L;
        Long reviewId = 2L;
        String username = "user1";

        Company company = new Company();
        User user = new User();
        user.setUsername("otherUser");
        Review review = new Review();
        review.setUser(user);

        when(companyService.getCompanyById(companyId)).thenReturn(company);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        boolean result = reviewService.deleteReview(companyId, reviewId, username);
        assertFalse(result);
        verify(reviewRepository, never()).deleteById(anyLong());
    }
}
