package es.codeurjc.ais.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.codeurjc.ais.book.OpenLibraryService;
import es.codeurjc.ais.book.OpenLibraryService.BookData;

@DisplayName("OpenLibraryService integration tests")
public class OpenLibraryServiceIntegrationTest {

    OpenLibraryService openLibraryService = new OpenLibraryService();
    
    @Test
	public void obtainListOfBooksByTopicTest(){
       
        List<BookData> books = openLibraryService.searchBooks("drama", 15);

        assertEquals(15, books.size());
  
    }
    
}
