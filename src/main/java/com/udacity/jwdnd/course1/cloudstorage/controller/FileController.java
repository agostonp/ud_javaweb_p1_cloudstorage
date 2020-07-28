package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.model.MFile;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        System.out.printf("In postFileUpload: \"\s\"\n", fileUpload.getOriginalFilename());
        String error = null;

        if(fileUpload.getOriginalFilename() == null || fileUpload.getOriginalFilename().isBlank()) {
            error = "Please select a file!";
        }

        if(error == null
           && !fileService.isFileNameValid(fileUpload.getOriginalFilename(), authentication.getName())) {
            error = "File with this name is already uploaded.";
        }
        
        if(error == null) {
            try {
                fileService.uploadFile(fileUpload, authentication.getName());
            }
            catch(IOException e) {
                error = "File upload failed: " + e.getMessage();
            }
        }

        model.addAttribute("fileList", fileService.listFileNames(authentication.getName()));

        if(error == null)
            model.addAttribute("uploadSuccess", true);
        else
            model.addAttribute("uploadError", error);

        return "home";
    }


    @GetMapping("/file-delete")
    public String getFileDelete(Authentication authentication, @RequestParam("fileId") Integer fileId, Model model) {
        System.out.println("In getFileDelete:" + fileId);

        fileService.deleteFile(fileId, authentication.getName());;

        model.addAttribute("fileList", fileService.listFileNames(authentication.getName()));
        return "home";
    }


    @GetMapping("/file-download")
    public ResponseEntity<byte[]> getFileDownload(Authentication authentication, @RequestParam("fileId") Integer fileId) throws IOException {
        System.out.println("In getFileDownload:" + fileId);

        MFile file = fileService.downloadFile(fileId, authentication.getName());

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(file.getContentType()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
            .body(file.getFileData().readAllBytes());
    }
}