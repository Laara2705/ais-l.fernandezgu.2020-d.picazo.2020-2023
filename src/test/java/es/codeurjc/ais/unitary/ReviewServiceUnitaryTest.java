package es.codeurjc.ais.unitary;

import es.codeurjc.ais.book.BookDetail;
import es.codeurjc.ais.book.BookService;
import es.codeurjc.ais.book.OpenLibraryService;
import es.codeurjc.ais.notification.NotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
/**
 * This class contains all tests about unitary testing
 * Proving the logic of a component
 * @author Diego Picazo, Lara Fernandez
 * @since 2023-03-07
 */
@DisplayName("Testing BookService class ...")
public class ReviewServiceUnitaryTest {
	static BookService bookService = null;
	static OpenLibraryService openLibraryService = null;
	static NotificationService notificationService = null;
	static String success;
	static String fail;
	@BeforeAll
	public static void setUp(){
		// Instance used for both Tests
		bookService = new BookService((openLibraryService = mock(OpenLibraryService.class)),
				(notificationService = mock(NotificationService.class)));
		success = "NotificationService: SUCCESS!";
		fail = "NotificationService: FAILED!";
	}
	@Test
	@DisplayName("Got 2 magic books & notification served")
	public void testUNIT_Magic(){
		/* Given */
		int magic_books;
		final int NUM_BOOKS = 2;
		/* When */
		when(openLibraryService.searchBooks("magic", 10))
				.thenReturn(Arrays.asList(
						new OpenLibraryService.BookData(null, "/work/01", null, null, null),
						new OpenLibraryService.BookData(null, "/work/02", null, null, null)));
		magic_books = bookService.findAll("magic").size();
		notificationService.info(success);
		/* Then */
		Assertions.assertEquals(NUM_BOOKS, magic_books);
		// Verify success message
		verify(notificationService).info(success);
	}
	@Test
	@DisplayName("Got null book & notification error served")
	public void testUNIT_NonRecover(){
		/* Given */
		Optional<BookDetail> bookDetailFail;
		/* When */
		// invalid id
		when(openLibraryService.getBook("anyString()"))
				.thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(404)));
		bookDetailFail = bookService.findById("anyString()");
		notificationService.error(fail);
		/* Then */
		Assertions.assertSame(Optional.empty(), bookDetailFail,
				"bookDetail should be empty");
		// Verify error message
		verify(notificationService).error(fail);
	}
	@Test
	@DisplayName("EXTRA: Checking good ID-book passed")
	// EXTRA test checking what happens if a good ID is passed
	public void testUNIT_EXTRA(){
		/* Given */
		Optional<BookDetail> bookDetailGood;
		// random valid id of an existing book
		when(openLibraryService.getBook("OL259010W"))
				.thenReturn(
						new OpenLibraryService.BookData(null, "/work/01",
								null, new Integer[1], new String[]{"HELLO WORLD"}));
		bookDetailGood = bookService.findById("OL259010W");
		Assertions.assertNotSame(Optional.empty(), bookDetailGood,
				"bookDetail should not be empty");
	}
}
