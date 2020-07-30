package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("SignupController bean created");
    }

    @GetMapping
    public String getSignupView() {
        System.out.println("In getSignupView");
        return "signup";
    }

    @PostMapping
    public String postSignup(@ModelAttribute("user") User user, Model model) {
        System.out.println("In postSignup");

        String error = null;
        if(!userService.isUsernameAvailable(user.getUsername())) {
            error = "Username already exists";
        }

        if(error == null) {
            if(userService.createUser(user) < 0) {
                error = "Signup failed. Internal error.";
            }
        }

        if (error == null) {
            model.addAttribute("signupSuccess", true);
            return "login";
        } else {
            model.addAttribute("signupError", error);
        }

        return "signup";
    }

}