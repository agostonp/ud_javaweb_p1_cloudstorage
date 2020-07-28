package com.udacity.jwdnd.course1.cloudstorage.testpage;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class NotesPageTab {

    @FindBy(id="nav-notes-tab")
    private WebElement wNotesTabLink;

    @FindBy(id="add-note-button")
    private WebElement wAddNoteButton;
   
    @FindBy(id="note-title")
    private WebElement wModalNoteTitle;

    @FindBy(id="note-description")
    private WebElement wModalNoteDesc;

    @FindBy(id="note-save-button")
    private WebElement wModalNoteSaveButton;

    @FindBy(id="note-title-list")
    private List<WebElement> wNoteTitleList;

    @FindBy(id="note-description-list")
    private List<WebElement> wNoteDescriptionList;

    //private final WebDriver driver;

    public NotesPageTab(final WebDriver driver) {
        PageFactory.initElements(driver, this);
        //this.driver = driver;
    }

    public void showNotes() throws InterruptedException {
        wNotesTabLink.click();
        Thread.sleep(1000);
    }

    public void newNote(String title, String description) throws InterruptedException {
        wAddNoteButton.click();
        Thread.sleep(1000);
        fillNote(title, description);
        Thread.sleep(1000);
    }

    private void fillNote(String title, String description) throws InterruptedException {
        wModalNoteTitle.clear();
        wModalNoteTitle.sendKeys(title);
        wModalNoteDesc.clear();
        wModalNoteDesc.sendKeys(description);
        wModalNoteSaveButton.click();
        Thread.sleep(1000);
    }

    public List<Note> getNotes() {
        final List<Note> notes = new ArrayList<>();
        for(int i=0; i < wNoteTitleList.size(); i++) {
            notes.add(new Note(null, wNoteTitleList.get(i).getText(), wNoteDescriptionList.get(i).getText(), null));
        }
        return notes;
    }

}