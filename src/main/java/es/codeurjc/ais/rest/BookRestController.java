package es.codeurjc.ais.rest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.ais.book.Book;
import es.codeurjc.ais.book.BookDetail;
import es.codeurjc.ais.book.BookService;
import es.codeurjc.ais.review.Review;
import es.codeurjc.ais.review.ReviewService;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

	@Autowired
	private BookService bookService;

	@Autowired
	private ReviewService reviewService;

	@GetMapping("/")
	public Collection<Book> getBooks(@RequestParam String topic) {
		return bookService.findAll(topic);
	}

	@GetMapping("/{bookId}")
	public ResponseEntity<BookDetail> getBook(@PathVariable String bookId) {
		
		Optional<BookDetail> op = bookService.findById(bookId);

		if(op.isPresent()) {
			BookDetail bookDetail = op.get();
			List<Review> reviews = reviewService.findByBookId(bookDetail.getId());
			bookDetail.setReviews(reviews);
			return new ResponseEntity<>(bookDetail, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@PostMapping("/{bookId}/review")
	public ResponseEntity<Review> createReview(@PathVariable String bookId, @RequestBody Review review) {

		review.setBookId(bookId);

		if(reviewService.save(review)){
			return new ResponseEntity<>(review, HttpStatus.CREATED);
		}else{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/{bookId}/review/{reviewId}")
	public ResponseEntity deleteReview(@PathVariable String bookId, @PathVariable Long reviewId) {

		if(reviewService.delete(reviewId)){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

}
