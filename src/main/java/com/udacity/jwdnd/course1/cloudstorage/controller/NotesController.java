package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NotesService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotesController {
    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("NotesController bean created");
    }

    @PostMapping("/note-save")
    public String postNoteSave(Authentication authentication, @ModelAttribute("noteModal") Note note, Model model) {
        System.out.println("In postNoteSave: " + note);
        String error = null;

        if(error == null
           && !notesService.isNoteValid(note, authentication.getName())) {
            error = "Another note with the same title already exists!";
        }
        
        if(error == null) {
            try {
                notesService.saveNote(note, authentication.getName());
            }
            catch(IOException e) {
                error = "Saving note failed: " + e.getMessage();
            }
        }

        if(error == null)
            model.addAttribute("noteSavedSuccess", true);
        else
            model.addAttribute("noteError", error);

        model.addAttribute("notesList", notesService.listNotes(authentication.getName()));
        model.addAttribute("activeTab", "notes");

        return "home";
    }

}