package com.udacity.jwdnd.course1.cloudstorage.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;

import org.springframework.stereotype.Service;

@Service
public class CredentialsService {
    private final CredentialsMapper credentialsMapper;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("CredentialsService bean created");
    }

    public boolean isCredentialValid(CredentialForm credentialForm, String username) {

        if(credentialForm.getUrl() == null || credentialForm.getUrl().isBlank() ||
           credentialForm.getUsername() == null || credentialForm.getUsername().isBlank() ||
           credentialForm.getPassword() == null || credentialForm.getPassword().isBlank())   {
            return false;
        }

        int userid = userService.getUser(username).getUserid();

        return !credentialsMapper.existsByUrlUserWithAnotherId(credentialForm.getUrl(), credentialForm.getUsername(),
                                                               credentialForm.getCredentialId(), userid);
    }

    public int saveCredential(final CredentialForm credentialForm, final String username) throws IOException {
        System.out.printf("In CredentialsService::saveCredential: %s username: \"%s\"\n", credentialForm, username);

        int userid = userService.getUser(username).getUserid();

        String key = encryptionService.generateNewKey();
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), key);

        Credential credential
             = new Credential(credentialForm.getCredentialId(), credentialForm.getUrl(), credentialForm.getUsername(),
                              key, encryptedPassword, userid);

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

    public List<CredentialForm> listCredentials(String userName) {
        System.out.println("In CredentialsService::listCredentials");
        int userid = userService.getUser(userName).getUserid();
        List<Credential> credentialList = credentialsMapper.listByUser(userid);
        List<CredentialForm> cFormList = new ArrayList<CredentialForm>();
        for(Credential credential : credentialList) {
            System.out.println("credentialList: " + credential);
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            CredentialForm cForm = new CredentialForm(credential.getCredentialId(), credential.getUrl(), credential.getUsername(),
                                                      decryptedPassword, credential.getPassword());
            cFormList.add(cForm);
        }
        return cFormList;
    }
}
