package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NotesService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotesController {
    private final NotesService notesService;
    private final HomeController homeController;

    public NotesController(NotesService notesService, HomeController homeController) {
        this.notesService = notesService;
        this.homeController = homeController;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("NotesController bean created");
    }

    @PostMapping("/note-save")
    public String postNoteSave(Authentication authentication, @ModelAttribute("noteModal") Note note,
                               @ModelAttribute("credentialModal") CredentialForm credentialForm, Model model) {
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

        homeController.addCommonModelAttributes(model, authentication.getName(), "notes");

        return "home";
    }

    @GetMapping("/note-delete")
    public String getNoteDelete(Authentication authentication, @RequestParam("noteId") Integer noteId,
                                @ModelAttribute("noteModal") Note note, @ModelAttribute("credentialModal") CredentialForm credentialForm, Model model) {
        System.out.println("In getNoteDelete:" + noteId);

        notesService.deleteNote(noteId, authentication.getName());

        homeController.addCommonModelAttributes(model, authentication.getName(), "notes");

        return "home";
    }

}