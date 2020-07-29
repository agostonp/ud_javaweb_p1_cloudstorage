package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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

    public HomeController(FileService fileService, NotesService notesService) {
        this.fileService = fileService;
        this.notesService = notesService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("HomeController bean created");
    }


    @GetMapping("/home")
    public String getHomePage(Authentication authentication, @ModelAttribute("noteModal") Note note, Model model) {
        System.out.println("In getHomePage");
        model.addAttribute("fileList", fileService.listFileNames(authentication.getName()));
        model.addAttribute("notesList", notesService.listNotes(authentication.getName()));
        model.addAttribute("activeTab", "files");

        return "home";
    }
}