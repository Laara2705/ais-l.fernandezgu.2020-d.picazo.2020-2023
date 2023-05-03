package es.codeurjc.ais.integration;

import es.codeurjc.ais.book.OpenLibraryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
/**
 * This class contains all tests about integration testing
 * @author Diego Picazo, Lara Fernandez
 * @since 2023-03-07
 */
@DisplayName("Testing OpenLibraryService ...")
public class OpenLibraryServiceIntegrationTest {
	static OpenLibraryService openLibraryService = null;
	@BeforeAll
	public static void setUp(){
		openLibraryService = new OpenLibraryService();
	}
	@Test
	@DisplayName("Got 15 books from each topic with limit 15")
	public void testINTE_Limit(){
		/* Given */
		final int NUM_BOOKS = 15;
		int drama_books, magic_books, fantasy_books;
		/* When */
		drama_books = openLibraryService.searchBooks("drama", NUM_BOOKS).size();
		magic_books = openLibraryService.searchBooks("magic", NUM_BOOKS).size();
		fantasy_books = openLibraryService.searchBooks("fantasy", NUM_BOOKS).size();
		/* Then */
		Assertions.assertTrue(() -> {
			Assertions.assertEquals(NUM_BOOKS, drama_books,
					"Number of drama books should be 15");
			Assertions.assertEquals(NUM_BOOKS, magic_books,
					"Number of magic books should be 15");
			Assertions.assertEquals(NUM_BOOKS, fantasy_books,
					"Number of fantasy books should be 15");
			return true;
		});
	}
	@Test
	@DisplayName("Got The Name of the Wind's book without problem")
	public void testINTE_ExistingBook(){
		/* Given */
		final String ID = "OL8479867W";
		final String BOOK_TITLE = "The Name of the Wind";
		/* When & Then */
		Assertions.assertDoesNotThrow(() -> {
			OpenLibraryService.BookData book = openLibraryService.getBook(ID);
			Assertions.assertEquals(BOOK_TITLE, book.title,
					"Title of book should be The Name of the Wind");
		});
	}
}
