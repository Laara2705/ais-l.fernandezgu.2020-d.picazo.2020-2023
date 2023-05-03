package es.codeurjc.ais.review;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Review {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 50000)
    private String nickname;
    private String content;
    private String bookId;

    @CreationTimestamp
    private Date createdAt;

    public Review() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Review [id=" + id + ", nickname=" + nickname + ", content=" + content + ", bookId=" + bookId + "]";
    }
    
}
