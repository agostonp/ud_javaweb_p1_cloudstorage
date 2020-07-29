package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.*;

import com.udacity.jwdnd.course1.cloudstorage.testpage.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.testpage.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.testpage.SignupPage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserAndAccessAppTests {

	@LocalServerPort
	private int port;
	private String baseURL;

	private WebDriver driver;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}


	// ****************************************************************************
	// 1. Write Tests for User Signup, Login, and Unauthorized Access Restrictions.
	// ****************************************************************************

	// 1.1 Write a test that verifies that an unauthorized user can only access the login and signup pages.
	@Test
	public void unauthorizedUserAccess() {
		driver.get(baseURL + "/signup");
		assertEquals(baseURL + "/signup", driver.getCurrentUrl());
		assertEquals("Sign Up", driver.getTitle());

		driver.get(baseURL + "/login");
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
		assertEquals("Login", driver.getTitle());

		// Invalid and restricted page tests - all shoud go to login page
		driver.get(baseURL + "/home");
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
		assertEquals("Login", driver.getTitle());

		driver.get(baseURL);
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
		assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/raspberry");
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
		assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/file-delete");
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
		assertEquals("Login", driver.getTitle());
	}


	// 1.2 Write a test that signs up a new user, logs in, verifies that the home page is accessible,
	//     logs out, and verifies that the home page is no longer accessible.
	@Test
	public void newUserAccess() throws InterruptedException {
		String username = "jani";
		String password = "chat123";

		String expectedSignupSuccesMsg = "You successfully signed up! Please continue to the login page.";

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("János", "Szabó", username, password);
		assertEquals(expectedSignupSuccesMsg, signupPage.getSuccesMsg());

		assertTrue(signupPage.goLogin());
		signupPage = null;

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		loginPage = null;

		// Verify that this is the Home page
		HomePage homePage = new HomePage(driver);
		assertEquals(baseURL + "/home", driver.getCurrentUrl());
		assertEquals("Home", driver.getTitle());

		homePage.logout();
		homePage = null;

		// Verify redirection to Login page
		loginPage = new LoginPage(driver);
		assertEquals(baseURL + "/login?logout", driver.getCurrentUrl());
		assertEquals("Login", driver.getTitle());
		assertEquals("You have been logged out", loginPage.getLogoutMessage());

		// Verify that any further call to get home page is redirected to login page
		driver.get(baseURL + "/home");
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
		assertEquals("Login", driver.getTitle());

	}
}
