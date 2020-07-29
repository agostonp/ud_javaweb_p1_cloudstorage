package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NotesService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class HomeController {
    private final FileService fileService;
    private final NotesService notesService;
    private final CredentialsService credentialsService;

    public HomeController(FileService fileService, NotesService notesService, CredentialsService credentialsService) {
        this.fileService = fileService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("HomeController bean created");
    }


    @GetMapping("/home")
    public String getHomePage(Authentication authentication, @ModelAttribute("noteModal") Note note,
                              @ModelAttribute("credentialModal") Credential credential, Model model) {
        System.out.println("In getHomePage");
        addCommonModelAttributes(model, authentication.getName(), "files");

        return "home";
    }

    public void addCommonModelAttributes(Model model, String username, String activeTab) {
        model.addAttribute("fileList", fileService.listFileNames(username));
        model.addAttribute("notesList", notesService.listNotes(username));
        model.addAttribute("credentialsList", credentialsService.listCredentials(username));
        model.addAttribute("activeTab", activeTab);
    }
}