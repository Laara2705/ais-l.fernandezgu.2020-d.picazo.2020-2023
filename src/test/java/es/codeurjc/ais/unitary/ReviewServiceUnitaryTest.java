package es.codeurjc.ais.unitary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.codeurjc.ais.notification.NotificationService;
import es.codeurjc.ais.review.Review;
import es.codeurjc.ais.review.ReviewRepository;
import es.codeurjc.ais.review.ReviewService;

@DisplayName("BookService Unitary tests")
public class ReviewServiceUnitaryTest {

    @Test
    public void getReviewsByIdUnitTest(){

        Review review1 = new Review();
        review1.setBookId("123A");
        review1.setNickname("nickname1");
        review1.setContent("content1");

        Review review2 = new Review();
        review2.setBookId("123A");
        review2.setNickname("nickname2");
        review2.setContent("content2");

        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        when(reviewRepository.findByBookId("123A")).thenReturn(Arrays.asList(review1, review2));
        NotificationService notificationService = mock(NotificationService.class);
        ReviewService reviewService = new ReviewService(reviewRepository, notificationService);

        assertEquals(2, reviewService.findByBookId("123A").size());


    }

}
