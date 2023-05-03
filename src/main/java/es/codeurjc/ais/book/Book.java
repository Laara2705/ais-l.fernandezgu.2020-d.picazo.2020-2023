package es.codeurjc.ais.book;

public class Book {
	
	protected String title;
	protected String id;

	public Book() {}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + "]";
	}

}
