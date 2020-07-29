package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.testpage.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.testpage.CredentialsPageTab;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

// ****************************************************************************
// 3. Write Tests for Credential Creation, Viewing, Editing, and Deletion
// ****************************************************************************

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialsAppTests {

	@LocalServerPort
	private int port;
	private String baseURL;

	private WebDriver driver;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() throws InterruptedException {
		driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("d", "1"); // Using the default user for testing
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}


	// 3.1 Write a test that creates a set of credentials, verifies that they are displayed,
	// and verifies that the displayed password is encrypted.
	@Test
	public void createCredential() {
		String url = "http://www.google.com";
		String username = "peti";
		String password = "tiTOK";
		CredentialsPageTab credentialsPageTab = new CredentialsPageTab(driver);
		credentialsPageTab.showCredentials();
		credentialsPageTab.newCredential(url, username, password);

		List<Credential> credentials = credentialsPageTab.getCredentials();
		assertEquals(1, credentials.size());
		assertEquals(url, credentials.get(0).getUrl());
		assertEquals(username, credentials.get(0).getUsername());
		assertEquals(24, credentials.get(0).getPassword().length()); // Encrypted password length
	}


	// 3.2 Write a test that views an existing set of credentials,
	//     verifies that the viewable password is unencrypted, edits the credentials,
	//     and verifies that the changes are displayed
	@Test
	public void editCredential() {
		String url = "http://www.google.com";
		String username = "peti";
		String password = "tiTOK";
		String newUrl = "http://www.microsoft.com";
		String newUsername = "jani";
		String newPassword = "nem IS az";

		CredentialsPageTab credentialsPageTab = new CredentialsPageTab(driver);
		credentialsPageTab.showCredentials();
		credentialsPageTab.newCredential(url, username, password);

		assertTrue(credentialsPageTab.editCredential(0, newUrl, newUsername, newPassword));

		List<Credential> credentials = credentialsPageTab.getCredentials();
		assertEquals(1, credentials.size());
		assertEquals(newUrl, credentials.get(0).getUrl());
		assertEquals(newUsername, credentials.get(0).getUsername());
		assertEquals(24, credentials.get(0).getPassword().length()); // Encrypted password length
	}


	// 3.3 Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed
	@Test
	public void deleteCredential() {
		String url = "http://www.google.com";
		String username = "peti";
		String password = "tiTOK";

		CredentialsPageTab credentialsPageTab = new CredentialsPageTab(driver);
		credentialsPageTab.showCredentials();
		credentialsPageTab.newCredential(url, username, password);

		assertTrue(credentialsPageTab.deleteCredential(0));

		List<Credential> credentials = credentialsPageTab.getCredentials();
		assertEquals(0, credentials.size());
	}
}
