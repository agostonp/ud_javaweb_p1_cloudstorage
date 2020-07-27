package com.udacity.jwdnd.course1.cloudstorage.service;

import java.io.IOException;
import java.io.InputStream;
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

    public boolean isFileNameValid(String filePath, String username) {
        String fileName = filePath;
        System.out.println("!!!WARNING! isFileNameAvailable: DUMMY FILE PATH TO NAME CONVERSION IS USED");

        if(fileName == null || fileName.isBlank()) {
            return false;
        }

        int userid = userService.getUser(username).getUserid();

        return !fileMapper.checkFileExistsByName(fileName, userid);
    }

    public int uploadFile(final MultipartFile fileUpload, final String username) throws IOException {
        System.out.printf("In FileService::uploadFile: filename: %s username: %s\n", fileUpload.getOriginalFilename(), username);

        int userid = userService.getUser(username).getUserid();
        InputStream fis = null;
        try {
            fis = fileUpload.getInputStream();
            MFile file = new MFile(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                                Long.toString(fileUpload.getSize()), userid, fis);

            int fileId = fileMapper.insert(file);
            if(fileId < 1) {
                throw new IOException("Failed to insert file data to database");
            }
            return fileId;
        }
        finally {
            fis.close();
        }
    }

    public MFile downloadFile(final Integer fileId, final String username) {
        System.out.printf("In FileService::downloadFile fileId: %d username: %s\n", fileId, username );

        Integer userid = userService.getUser(username).getUserid();

        MFile file = fileMapper.getByUser(fileId, userid);
        System.out.println("In FileService::downloadFile File fields:/n" + file );
        
        return file;
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
