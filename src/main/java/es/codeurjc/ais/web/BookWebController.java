package es.codeurjc.ais.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.ais.book.BookDetail;
import es.codeurjc.ais.book.BookService;
import es.codeurjc.ais.review.Review;
import es.codeurjc.ais.review.ReviewService;

@Controller
public class BookWebController {

	@Autowired
	private BookService bookService;

	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/")
	public String showBooks(Model model, @RequestParam(defaultValue = "fantasy") String topic, @RequestParam Optional<Boolean> error) {

		model.addAttribute("books", bookService.findAll(topic));

		model.addAttribute("topic", topic);

		if (error.isPresent()){
			model.addAttribute("error", true);
			model.addAttribute("message", "Error when retrieving a book: unsupported format");
		}
		
		return "books";
	}
	
	@GetMapping("/books/{id}")
	public String showBook(Model model, @PathVariable String id, @RequestParam Optional<Boolean> error) {

		Optional<BookDetail> op = bookService.findById(id);

		if(op.isPresent()) {
			BookDetail book = op.get();
			List<Review> reviews = reviewService.findByBookId(id);
			model.addAttribute("book", book);
			model.addAttribute("reviews", reviews);
			if (error.isPresent()){
				model.addAttribute("error", true);
				model.addAttribute("message", "Error at saving the review: empty fields");
			}
			return "book";
		}else {
			return "redirect:/?error=true";
		}
		
	}

	@PostMapping("/review")
	public String newBookProcess(Model model, Review review) {
		if(reviewService.save(review)){
			return "redirect:/books/" + review.getBookId();
		}else{
			return "redirect:/books/" + review.getBookId() +"?error=true";
		}
	}

	@GetMapping("/book/{bookId}/review/{reviewId}/delete")
	public String removeBook(@PathVariable String bookId, @PathVariable long reviewId) {

		reviewService.delete(reviewId);
		
		return "redirect:/books/" + bookId;
		
	}
	


}
