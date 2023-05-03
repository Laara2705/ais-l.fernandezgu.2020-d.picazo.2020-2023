package es.codeurjc.ais.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import es.codeurjc.ais.book.OpenLibraryService.BookData;
import es.codeurjc.ais.notification.NotificationService;

@Service
public class BookService {

    private OpenLibraryService openLibraryService;

    private NotificationService notificationService;

    public BookService(OpenLibraryService openLibraryService, NotificationService notificationService) {
        this.openLibraryService = openLibraryService;
        this.notificationService = notificationService;
    }

	public List<Book> findAll(String subject) {
        List<Book> books = new ArrayList<>();
        List<BookData> unformatedBooks = openLibraryService.searchBooks(subject, 10);
        notificationService.info("The books have been loaded: "+unformatedBooks.size()+" books | query: "+subject);
		for(BookData bookData : unformatedBooks){
            Book book = new Book();
            book.setTitle(bookData.title);
            book.setId(bookData.key.split("/")[2]);
            books.add(book);
        }
        return books;
	}

    public Optional<BookDetail> findById(String id) {
        BookDetail bookDetail = new BookDetail();

        try{
            BookData bookData = openLibraryService.getBook(id);
            String imageUrl = "https://covers.openlibrary.org/b/id/"+bookData.covers[0]+"-M.jpg";
            bookDetail.setTitle(bookData.title);
            bookDetail.setId(bookData.key.split("/")[2]);
            bookDetail.setDescription(bookData.description);
            bookDetail.setImageUrl(imageUrl);
            String[] subjects = Arrays.stream(bookData.subjects).map(String::toLowerCase).toArray(String[]::new);
            bookDetail.setSubjects(subjects);
            notificationService.info("The book has been loaded: "+bookDetail.getTitle()+" | id: "+bookDetail.getId());
            return Optional.of(bookDetail);
        }catch(HttpClientErrorException e){
            notificationService.error("The book ("+id+") has not been loaded due "+e.getMessage());
            return Optional.empty();
        }catch(RestClientException e){
            notificationService.error("The book ("+id+") has not been loaded due "+e.getMessage());
            return Optional.empty();
        }
        
    }
}
