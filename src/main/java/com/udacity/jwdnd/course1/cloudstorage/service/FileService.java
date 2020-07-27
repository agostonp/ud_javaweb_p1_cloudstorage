package com.udacity.jwdnd.course1.cloudstorage.service;

import java.util.List;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.MFile;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("FileService bean created");
    }

    public boolean isFileNameValid(String filePath) {
        String fileName = filePath;
        System.out.println("!!!WARNING! isFileNameAvailable: DUMMY FILE PATH TO NAME CONVERSION IS USED");

        if(fileName == null || fileName.isEmpty()) {
            return false;
        }
            
        return !fileMapper.checkFileExistsByName(fileName);
    }

    public int uploadFile(final MultipartFile fileUpload, final String username) {
        System.out.println("In FileService::uploadFile:" + fileUpload.getOriginalFilename());

        int userid = userService.getUser(username).getUserid();

        //InputStream fis = fileUpload.getInputStream();
        MFile file = new MFile(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(), Long.toString(fileUpload.getSize()), userid);

        return fileMapper.insert(file);
    }

    public void deleteFile(final Integer fileId, final String username) {
        System.out.printf("In FileService::deleteFile fileId: %d username: %s\n", fileId, username );

        int userid = userService.getUser(username).getUserid();

        fileMapper.deleteByUser(fileId, userid);
    }

    public List<MFile> listFileNames(String userName) {
        System.out.println("In FileService::listFileNames");
        int userid = userService.getUser(userName).getUserid();
        List<MFile> fileList = fileMapper.listFileNames(userid);
        for(MFile file : fileList) {
            System.out.printf("fileList id: %d name:%s\n", file.getFileId(), file.getFileName());
        }
        return fileList;
    }
}
