package com.udacity.jwdnd.course1.cloudstorage.testpage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    
    @FindBy(id = "inputFirstName")
    private WebElement wFirstName;

    @FindBy(id = "inputLastName")
    private WebElement wLastName;

    @FindBy(id = "inputUsername")
    private WebElement wUsername;

    @FindBy(id = "inputPassword")
    private WebElement wPassword;

    @FindBy(id = "submit-button")
    private WebElement wSubmitButton;

    private final WebDriver driver;

    public SignupPage(final WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void signup(final String firstName, final String lastName, final String username, final String password) {
        wFirstName.clear();
        wFirstName.sendKeys(firstName);
        wLastName.clear();
        wLastName.sendKeys(lastName);
        wUsername.clear();
        wUsername.sendKeys(username);
        wPassword.clear();
        wPassword.sendKeys(password);
        wSubmitButton.click();
    }

    public String getSuccesMsg() throws NoSuchElementException {
        return driver.findElement(By.id("success-msg")).getText();
    }

    // This can only be used after successful signup
    public boolean goLogin() {
        try {
            WebElement wLogin = driver.findElement(By.id("login-link"));
            driver.get(wLogin.getAttribute("href"));
            return true;
        } catch(NoSuchElementException e) {
            return false;
        }        
    }

}