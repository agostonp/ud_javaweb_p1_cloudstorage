package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.service.FileService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("FileController bean created");
    }

    @GetMapping("/home")
    public String getFileTab(Authentication authentication, Model model) {
        System.out.println("In getFileTab");
        model.addAttribute("fileList", fileService.listFileNames(authentication.getName()));
        return "home";
    }

    @PostMapping("/file-upload")
    public String postFileUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload, Model model) {
        System.out.println("In postFileUpload:" + fileUpload.getOriginalFilename());

        fileService.uploadFile(fileUpload, authentication.getName());

        model.addAttribute("fileList", fileService.listFileNames(authentication.getName()));
        return "home";
    }

    @GetMapping(value="/file-delete")
    public String postFileDelete(Authentication authentication, @RequestParam("fileId") Integer fileId, Model model) {
        System.out.println("In postFileDelete:" + fileId);

        fileService.deleteFile(fileId, authentication.getName());;

        model.addAttribute("fileList", fileService.listFileNames(authentication.getName()));
        return "home";
    }
}