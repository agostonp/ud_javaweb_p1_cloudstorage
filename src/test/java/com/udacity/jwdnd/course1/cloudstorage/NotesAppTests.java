package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.testpage.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.testpage.NotesPageTab;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

// ****************************************************************************
// 2. Write Tests for Note Creation, Viewing, Editing, and Deletion
// ****************************************************************************

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeEachTest.sql")
class NotesAppTests {

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


	// 2.1 Write a test that creates a note, and verifies it is displayed
	@Test
	public void createNote() {
		String title = "Test Note creation";
		String description = "// 2.1 Write a test that creates a note,\n and verifies it is displayed";
		NotesPageTab notesPageTab = new NotesPageTab(driver);
		notesPageTab.showNotes();
		notesPageTab.newNote(title, description);

		List<Note> notes = notesPageTab.getNotes();
		assertEquals(1, notes.size());
		assertEquals(title, notes.get(0).getNoteTitle());
		assertEquals(description.replace("\n", ""), notes.get(0).getNoteDescription());
	}


	// 2.2 Write a test that edits an existing note and verifies that the changes are displayed
	@Test
	public void editNote() {
		String title = "Test Note creation";
		String description = "// 2.1 Write a test that creates a note,\n and verifies it is displayed";
		String newTitle = "Test Note Editing";
		String newDescription = "// 2.2 Write a test that edits an existing note\n and verifies that the changes are displayed";
		NotesPageTab notesPageTab = new NotesPageTab(driver);
		notesPageTab.showNotes();
		notesPageTab.newNote(title, description);

		assertTrue(notesPageTab.editNote(0, newTitle, newDescription));

		List<Note> notes = notesPageTab.getNotes();
		assertEquals(1, notes.size());
		assertEquals(newTitle, notes.get(0).getNoteTitle());
		assertEquals(newDescription.replace("\n", ""), notes.get(0).getNoteDescription());
	}


	// 2.3 Write a test that deletes a note and verifies that the note is no longer displayed
	@Test
	public void deleteNote() {
		String title = "Test Note Deletion";
		String description = "// 2.3 Write a test that deletes a note\n and verifies that the note is no longer displayed";
		NotesPageTab notesPageTab = new NotesPageTab(driver);
		notesPageTab.showNotes();
		notesPageTab.newNote(title, description);

		assertTrue(notesPageTab.deleteNote(0));

		List<Note> notes = notesPageTab.getNotes();
		assertEquals(0, notes.size());
	}
}
