package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.service.FileService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    private final FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("HomeController bean created");
    }


    @GetMapping("/home")
    public String getHomePage(Authentication authentication, Model model) {
        System.out.println("In getHomePage");
        model.addAttribute("fileList", fileService.listFileNames(authentication.getName()));
        return "home";
    }
}