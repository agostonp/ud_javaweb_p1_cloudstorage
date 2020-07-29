package com.udacity.jwdnd.course1.cloudstorage.testpage;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CredentialsPageTab {

    @FindBy(id="nav-credentials-tab")
    private WebElement wCredentialsTabLink;

    @FindBy(id="add-credential-button")
    private WebElement wAddCredentialButton;
   
    @FindBy(id="credential-url")
    private WebElement wModalCredentialUrl;

    @FindBy(id="credential-username")
    private WebElement wModalCredentialUsername;

    @FindBy(id="credential-password")
    private WebElement wModalCredentialPassword;

    @FindBy(id="credential-save-button")
    private WebElement wModalCredentialSaveButton;

    @FindBy(id="credential-edit-list")
    private List<WebElement> wCredentialEditList;

    @FindBy(id="credential-delete-list")
    private List<WebElement> wCredentialDeleteList;

    @FindBy(id="credential-url-list")
    private List<WebElement> wCredentialUrlList;

    @FindBy(id="credential-username-list")
    private List<WebElement> wCredentialUsernameList;

    @FindBy(id="credential-password-list")
    private List<WebElement> wCredentialPasswordList;

    //private final WebDriver driver;
    private final WebDriverWait wait;

    public CredentialsPageTab(final WebDriver driver) {
        this.wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-save-button")));

        PageFactory.initElements(driver, this);
        //this.driver = driver;
    }

    public void showCredentials() {
        wait.until(ExpectedConditions.elementToBeClickable(wCredentialsTabLink));
        try { Thread.sleep(500); }
        catch(InterruptedException e) {
            System.out.println(e);
        }
        wCredentialsTabLink.click();
    }

    public void newCredential(String url, String username, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(wAddCredentialButton));
        wAddCredentialButton.click();
        fillCredential(url, username, password);
    }

    public boolean editCredential(int index, String newUrl, String newUsername, String newPassword) {
        if(wCredentialEditList.size() <= index)
            return false;
        wait.until(ExpectedConditions.elementToBeClickable(wCredentialEditList.get(index)));
        try { Thread.sleep(500); }
        catch(InterruptedException e) { System.out.println(e);}

        wCredentialEditList.get(index).click();
        fillCredential(newUrl, newUsername, newPassword);
        return true;
    }

    private void fillCredential(String url, String username, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(wModalCredentialSaveButton));
        wModalCredentialUrl.clear();
        wModalCredentialUrl.sendKeys(url);
        wModalCredentialUsername.clear();
        wModalCredentialUsername.sendKeys(username);
        wModalCredentialPassword.clear();
        wModalCredentialPassword.sendKeys(password);
        wModalCredentialSaveButton.click();
    }

    public boolean deleteCredential(int index) {
        if(wCredentialDeleteList.size() <= index)
            return false;
        wait.until(ExpectedConditions.elementToBeClickable(wCredentialDeleteList.get(index)));
        try { Thread.sleep(500); }
        catch(InterruptedException e) { System.out.println(e);}

        wCredentialDeleteList.get(index).click();
        return true;
    }

    public List<Credential> getCredentials() {
        final List<Credential> credentials = new ArrayList<>();
        for(int i=0; i < wCredentialUrlList.size(); i++) {
            credentials.add(new Credential(null, wCredentialUrlList.get(i).getText(), wCredentialUsernameList.get(i).getText(), null, wCredentialPasswordList.get(i).getText(), null));
        }
        return credentials;
    }

}