package es.codeurjc.ais.e2e.selenium;

import es.codeurjc.ais.Application;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
/**
 * This class contains all tests about E2E testing
 * Proving the web interface using Selenium
 * @author Diego Picazo, Lara Fernandez
 * @since 2023-03-07
 */
@SpringBootTest(classes = Application.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Testing web interface ...")
public class SeleniumTest{
	@LocalServerPort
	int port;
	private WebDriver driver;
	
	@BeforeAll
	public static void setUp(){
		WebDriverManager.firefoxdriver().setup();
	}
	@BeforeEach
	public void setUpTest(){
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.addArguments("--headless");
		driver = new FirefoxDriver(firefoxOptions);
		driver.get("http://localhost:"+this.port+"/");
	}
	@AfterEach
	public void teardown(){
		if (driver != null)
			driver.quit();
	}
	@Test
	@DisplayName("Checked first drama-book has drama tag")
	public void testSELE_DramaTag(){
		/* When */
		driver.findElement(By.cssSelector(".ui.input"))
				.findElement(By.name("topic")).sendKeys("drama");
		driver.findElement(By.cssSelector(".ui.input"))
				.findElement(By.id("search-button")).click();
		driver.findElement(By.cssSelector(".book:nth-child(1)")).click();
		/* Then */
		Assertions.assertNotNull(driver.findElement(By.id("drama")),
				"Drama tag should be in this book");
	}
	@Test
	@DisplayName("Checked the creation of a The Way of Kings' review")
	public void testSELE_KingsReview(){
		/* Given */
		final String NICKNAME = "Rodolfo";
		final String INFO = "I love this book!";
		/* When */
		driver.findElement(By.cssSelector(".ui.input"))
				.findElement(By.name("topic")).sendKeys("epic fantasy");
		driver.findElement(By.cssSelector(".ui.input"))
				.findElement(By.id("search-button")).click();
		driver.findElement(By.cssSelector(".ui.list"))
				.findElement(By.id("The Way of Kings")).click();
		driver.findElement(By.cssSelector(".ui.secondary.segment"))
				.findElement(By.cssSelector(".ui.form"))
				.findElement(By.name("nickname")).sendKeys(NICKNAME);
		driver.findElement(By.cssSelector(".ui.secondary.segment"))
				.findElement(By.cssSelector(".ui.form"))
				.findElement(By.name("content")).sendKeys(INFO);
		driver.findElement(By.cssSelector(".ui.secondary.segment"))
				.findElement(By.cssSelector(".ui.form"))
				.findElement(By.id("add-review")).click();
		/* Then */
		Assertions.assertTrue(() -> {
			Assertions.assertEquals(NICKNAME, driver.findElement(By.className("comment"))
						.findElement(By.className("author")).getText(),
				"Nickname should be Rodolfo");
			Assertions.assertEquals(INFO, driver.findElement(By.className("comment"))
						.findElement(By.className("text")).getText(),
				"Content should be 'I love this book!'");
			return true;
		});
	}
	@Test
	@DisplayName("Checked the bad-creation of an empty review")
	public void testSELE_WordsReview(){
		/* When */
		driver.findElement(By.cssSelector(".ui.input"))
				.findElement(By.name("topic")).sendKeys("epic fantasy");
		driver.findElement(By.cssSelector(".ui.input"))
				.findElement(By.id("search-button")).click();
		driver.findElement(By.cssSelector(".ui.list"))
				.findElement(By.id("Words of Radiance")).click();
		driver.findElement(By.cssSelector(".ui.secondary.segment"))
				.findElement(By.cssSelector(".ui.form"))
				.findElement(By.name("nickname")).sendKeys("");
		driver.findElement(By.cssSelector(".ui.secondary.segment"))
				.findElement(By.cssSelector(".ui.form"))
				.findElement(By.name("content")).sendKeys("");
		driver.findElement(By.cssSelector(".ui.secondary.segment"))
					.findElement(By.cssSelector(".ui.form"))
					.findElement(By.id("add-review")).click();
		/* Then */
		Assertions.assertNotNull(driver.findElement(By.cssSelector(".ui.comments"))
				.findElement(By.cssSelector(".ui.negative.message")),
				"Error message should not be null");
	}
}
