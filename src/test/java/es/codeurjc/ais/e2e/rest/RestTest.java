package es.codeurjc.ais.e2e.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import java.util.List;
import java.util.NoSuchElementException;

import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;
/**
 * This class contains all tests about API REST
 * @author Diego Picazo, Lara Fernandez
 * @since 2023-03-07
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Testing API REST ...")
public class RestTest {
	@LocalServerPort
	int port;
	
	@BeforeEach
	public void setUp(){
		RestAssured.port = port;
	}
	@Test
	@DisplayName("Got 10 drama-books")
	public void testREST_TenDrama(){
		/* Given */
		final int NUM_BOOKS = 10;
		int drama_books;
		/* When */
		Response response_drama = given().contentType(ContentType.JSON)
				.when().get("/api/books/?topic={topic}", "drama").thenReturn();
		JsonPath jsonPath_drama = from(response_drama.getBody().asString());
		drama_books = jsonPath_drama.getInt("size()");
		/* Then */
		Assertions.assertEquals(NUM_BOOKS, drama_books,
				"Number of books should be 10.");
	}
	@Test
	@DisplayName("Checked the creation of a fantasy's book review")
	public void testREST_FantasyReview() throws JSONException {
		/* Given */
		Response response_fantasy, response_book;
		JSONArray jsonArray_fantasy;
		Response response_review;
		JSONArray jsonArray_reviews;
		JsonPath jsonPath_review, jsonPath_book;
		JSONObject body_review = new JSONObject();
		final String NICKNAME = "Rodolfo";
		final String CONTENT = "I loved this book!";
		/* When */
		response_fantasy = given().contentType(ContentType.JSON)
				.when().get("/api/books/?topic={topic}", "fantasy").thenReturn();
		jsonArray_fantasy = new JSONArray(response_fantasy.getBody().asString());
		body_review.put("nickname", NICKNAME);
		body_review.put("content", CONTENT);
		response_review = given().
			contentType(ContentType.JSON).
			body(body_review.toString()).
		when().
			  post("/api/books/{bookId}/review", jsonArray_fantasy.getJSONObject(0).getString("id")).
		/* Then */
		then()
			.statusCode(201)
			.body("nickname", equalTo(NICKNAME))
			.body("content", equalTo(CONTENT)).extract().response().andReturn();
		response_book = given().contentType(ContentType.JSON)
				.when().get("/api/books/{bookId}",
						jsonArray_fantasy.getJSONObject(0).getString("id")).thenReturn();
		jsonPath_book = from(response_book.getBody().asString());
		jsonPath_review = from(response_review.getBody().asString());
		jsonArray_reviews = new JSONArray((List<?>) jsonPath_book.get("reviews"));
		Assertions.assertDoesNotThrow(() -> {
			for(int i = 0; i < jsonArray_reviews.length(); i++){
				if (Long.valueOf(jsonPath_review.getString("id"))
						.equals(Long.parseLong(jsonArray_reviews.getJSONObject(i).getString("id"))))
					return;
			}
			throw new NoSuchElementException();
		}, "Review posted should exist.");
	}
	@Test
	@DisplayName("Checked the removal of a The Way of Kings' created review")
	public void testREST_KingsReview() throws JSONException {
		/* Given */
		final String BOOK_ID = "OL15358691W";
		final String NICKNAME = "Rodolfo";
		final String CONTENT = "I loved this book!";
		JSONObject body = new JSONObject();
		Response response_review;
		JsonPath jsonPath_review;
		Response response_book;
		JsonPath jsonPath_book;
		JSONArray jsonArray_reviews;
		/* When */
		body.put("nickname", NICKNAME);
		body.put("content", CONTENT);
		response_review = given().
			contentType(ContentType.JSON).
			body(body.toString()).
		when().
			  post("/api/books/{bookId}/review", BOOK_ID).
		then()
			.statusCode(201)
			.body("nickname", equalTo(NICKNAME))
			.body("content", equalTo(CONTENT))
			.extract().response().andReturn();
		jsonPath_review = from(response_review.getBody().asString());
		given().
				contentType(ContentType.JSON).
				body(body.toString()).
		when().
				delete("/api/books/{bookId}/review/{reviewId}", BOOK_ID, jsonPath_review.get("id")).
		/* Then */
		then()
				.statusCode(204);
		response_book = given().when().get("/api/books/{bookId}", BOOK_ID).thenReturn();
		jsonPath_book = from(response_book.getBody().asString());
		jsonArray_reviews = new JSONArray((List<?>) jsonPath_book.get("reviews"));
		Assertions.assertDoesNotThrow(() -> {
			for(int i = 0; i < jsonArray_reviews.length(); i++){
				if (Long.valueOf(jsonArray_reviews.getJSONObject(i).getString("id"))
						.equals(Long.parseLong(jsonPath_review.getString("id")))){
					throw new IllegalArgumentException();
				}
			}
		}, "Deleted review shouldn't exist.");
	}
}
