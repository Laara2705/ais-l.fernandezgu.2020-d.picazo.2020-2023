package es.codeurjc.ais.book;

import java.util.ArrayList;
import java.util.List;

import es.codeurjc.ais.review.Review;

public class BookDetail extends Book{

    private String description;

    private String imageUrl;

    private String[] subjects;

    private List<Review> reviews = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "BookDetail [description=" + description + ", imageUrl=" + imageUrl + "]";
    }

    public List<Review> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }
}
