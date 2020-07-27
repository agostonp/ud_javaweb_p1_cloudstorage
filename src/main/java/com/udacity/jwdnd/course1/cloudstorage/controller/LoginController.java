package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @PostConstruct
    public void postConstruct() {
        System.out.println("LoginController bean created");
    }

    @GetMapping
    public String getLoginView() {
        System.out.println("In getLoginView");
        return "login";
    }

}