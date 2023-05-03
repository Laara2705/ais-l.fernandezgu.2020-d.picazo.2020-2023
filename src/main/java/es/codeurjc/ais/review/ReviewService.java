package es.codeurjc.ais.review;

import java.util.List;

import org.springframework.stereotype.Service;

import es.codeurjc.ais.notification.NotificationService;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private NotificationService notificationService;
    
    public ReviewService(ReviewRepository reviewRepository, NotificationService notificationService) {
        this.reviewRepository = reviewRepository;
        this.notificationService = notificationService;
    }

    public List<Review> findByBookId(String bookId) {
        return reviewRepository.findByBookId(bookId);
    }
    
    public boolean save(Review review) {
        if(review.getNickname().isEmpty() || review.getContent().isEmpty()){
            notificationService.error("Error at saving the review: empty fields");
			return false;
		}
        reviewRepository.save(review);
        notificationService.info("Review saved, " + review.getNickname() + " posted " + review.getContent() + " of book with id " + review.getBookId());
        return true;
    }

    public boolean delete(Long id) {
        if(!reviewRepository.existsById(id)){
            notificationService.error("Error at deleting the review: review with id " + id + " does not exist");
            return false;
        }
        reviewRepository.deleteById(id);
        notificationService.info("Review deleted, review with id=" + id + " deleted");
        return true;
    }

}
