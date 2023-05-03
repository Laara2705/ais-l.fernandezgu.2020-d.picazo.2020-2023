package es.codeurjc.ais.book;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenLibraryService {

    public static class BooksResponse {
        public List<BookData> works;
    }
    
    public static class BookData {
        public String title;
        public String key;
        public String description;
        public Integer[] covers;
        public String[] subjects;
        public String[] subject;
        public BookData() {}
        public BookData(String title, String key, String description, Integer[] covers, String[] subjects) {
            this.title = title;
            this.key = key;
            this.description = description;
            this.covers = covers;
            this.subjects = subjects;
        }
    }

    private RestTemplate restTemplate;

    public OpenLibraryService() {
        this.restTemplate = new RestTemplate();
    }

    public List<BookData> searchBooks(String subject, int limit) {
        String url = "https://openlibrary.org/subjects/"+subject.toLowerCase()+".json?limit="+limit;
        BooksResponse response = restTemplate.getForObject(url, BooksResponse.class);
        return response.works;
    }

    public BookData getBook(String id) {
        return restTemplate.getForObject("https://openlibrary.org/works/"+id+".json", BookData.class);
    }
    
}
