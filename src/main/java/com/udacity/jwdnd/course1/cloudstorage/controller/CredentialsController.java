package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialsService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialsController {
    private final CredentialsService credentialsService;
    private final HomeController homeController;


    public CredentialsController(CredentialsService credentialsService, HomeController homeController) {
        this.credentialsService = credentialsService;
        this.homeController = homeController;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("CredentialsController bean created");
    }

    @PostMapping("/credential-save")
    public String postCredentialSave(Authentication authentication, @ModelAttribute("noteModal") Note note,
                                     @ModelAttribute("credentialModal") CredentialForm credentialForm, Model model) {
        System.out.println("In postCredentialSave: " + credentialForm);
        String error = null;

        if(error == null
           && !credentialsService.isCredentialValid(credentialForm, authentication.getName())) {
            error = "Another credential with the same url and username already exists!";
        }
        
        if(error == null) {
            try {
                credentialsService.saveCredential(credentialForm, authentication.getName());
            }
            catch(IOException e) {
                error = "Saving credential failed: " + e.getMessage();
            }
        }

        if(error == null) {
            model.addAttribute("credentialSavedSuccess", true);
        }
        else {
            model.addAttribute("credentialError", error);
        }

        homeController.addCommonModelAttributes(model, authentication.getName(), "credentials");

        return "home";
    }

    @GetMapping("/credential-delete")
    public String getCredentialDelete(Authentication authentication, @RequestParam("credentialId") Integer credentialId,
                        @ModelAttribute("noteModal") Note note, @ModelAttribute("credentialModal") CredentialForm credentialForm, Model model) {
        System.out.println("In getCredentialDelete:" + credentialId);

        credentialsService.deleteCredential(credentialId, authentication.getName());

        homeController.addCommonModelAttributes(model, authentication.getName(), "credentials");

        return "home";
    }

}