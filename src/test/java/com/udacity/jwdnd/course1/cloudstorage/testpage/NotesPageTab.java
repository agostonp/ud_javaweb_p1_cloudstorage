package com.udacity.jwdnd.course1.cloudstorage.testpage;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


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

    @FindBy(id="note-edit-list")
    private List<WebElement> wNoteEditList;

    @FindBy(id="note-delete-list")
    private List<WebElement> wNoteDeleteList;

    @FindBy(id="note-title-list")
    private List<WebElement> wNoteTitleList;

    @FindBy(id="note-description-list")
    private List<WebElement> wNoteDescriptionList;

    //private final WebDriver driver;
    private final WebDriverWait wait;

    public NotesPageTab(final WebDriver driver) {
        this.wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-save-button")));

        PageFactory.initElements(driver, this);
        //this.driver = driver;
    }

    public void showNotes() {
        wait.until(ExpectedConditions.elementToBeClickable(wNotesTabLink));
        try { Thread.sleep(500); }
        catch(InterruptedException e) {
            System.out.println(e);
        }
        wNotesTabLink.click();
    }

    public void newNote(String title, String description) {
        wait.until(ExpectedConditions.elementToBeClickable(wAddNoteButton));
        wAddNoteButton.click();
        fillNote(title, description);
    }

    public boolean editNote(int index, String newTitle, String newDescription) {
        if(wNoteEditList.size() <= index)
            return false;
        wait.until(ExpectedConditions.elementToBeClickable(wNoteEditList.get(index)));
        try { Thread.sleep(500); }
        catch(InterruptedException e) { System.out.println(e);}

        wNoteEditList.get(index).click();
        fillNote(newTitle, newDescription);
        return true;
    }

    private void fillNote(String title, String description) {
        wait.until(ExpectedConditions.elementToBeClickable(wModalNoteSaveButton));
        wModalNoteTitle.clear();
        wModalNoteTitle.sendKeys(title);
        wModalNoteDesc.clear();
        wModalNoteDesc.sendKeys(description);
        wModalNoteSaveButton.click();
    }

    public boolean deleteNote(int index) {
        if(wNoteDeleteList.size() <= index)
            return false;
        wait.until(ExpectedConditions.elementToBeClickable(wNoteDeleteList.get(index)));
        try { Thread.sleep(500); }
        catch(InterruptedException e) { System.out.println(e);}

        wNoteDeleteList.get(index).click();
        return true;
    }

    public List<Note> getNotes() {
        final List<Note> notes = new ArrayList<>();
        for(int i=0; i < wNoteTitleList.size(); i++) {
            notes.add(new Note(null, wNoteTitleList.get(i).getText(), wNoteDescriptionList.get(i).getText(), null));
        }
        return notes;
    }

}