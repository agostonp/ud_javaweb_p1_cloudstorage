package com.udacity.jwdnd.course1.cloudstorage.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import org.springframework.stereotype.Service;

@Service
public class CredentialsService {
    private final CredentialsMapper credentialsMapper;
    private final UserService userService;

    public CredentialsService(CredentialsMapper credentialsMapper, UserService userService) {
        this.credentialsMapper = credentialsMapper;
        this.userService = userService;
    }
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("CredentialsService bean created");
    }

    public boolean isCredentialValid(Credential credential, String username) {

        if(credential.getUrl() == null || credential.getUrl().isBlank() ||
           credential.getUsername() == null || credential.getUsername().isBlank() ||
           credential.getPassword() == null || credential.getPassword().isBlank())   {
            return false;
        }

        int userid = userService.getUser(username).getUserid();

        return !credentialsMapper.existsByUrlUserWithAnotherId(credential.getUrl(), credential.getUsername(), credential.getCredentialId(), userid);
    }

    public int saveCredential(final Credential credential, final String username) throws IOException {
        System.out.printf("In CredentialsService::saveCredential: %s username: \"%s\"\n", credential, username);

        int userid = userService.getUser(username).getUserid();
        credential.setUserid(userid);

        int credentialId = 0;
        if(credential.getCredentialId() == null) {
            credentialId = credentialsMapper.insert(credential);
            System.out.println("Credential insterted into database with credentialId:" + credentialId);
        }
        else {
            credentialId = credentialsMapper.updateByUser(credential);
            System.out.println("Credential updated in database with credentialId:" + credentialId);
        }

        if(credentialId < 1) {
            throw new IOException("Failed to insert/update Credential data to database");
        }
        return credentialId;
    }

    public void deleteCredential(final Integer credentialId, final String username) {
        System.out.printf("In CredentialsService::deleteCredential credentialId: %d username: %s\n", credentialId, username );

        int userid = userService.getUser(username).getUserid();

        credentialsMapper.deleteByUser(credentialId, userid);
    }

    public List<Credential> listCredentials(String userName) {
        System.out.println("In CredentialsService::listCredentials");
        int userid = userService.getUser(userName).getUserid();
        List<Credential> credentialList = credentialsMapper.listByUser(userid);
        for(Credential credential : credentialList) {
            System.out.printf("credentialList id: %d url:%s username:%s\n", credential.getCredentialId(), credential.getUrl(), credential.getUsername());
        }
        return credentialList;
    }
}
