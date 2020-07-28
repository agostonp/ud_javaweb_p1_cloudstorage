package com.udacity.jwdnd.course1.cloudstorage.testpage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement wUsername;

    @FindBy(id = "inputPassword")
    private WebElement wPassword;

    @FindBy(id = "submit-button")
    private WebElement wSubmitButton;

    @FindBy(id = "signup-link")
    private WebElement wSignupLink;

    private final WebDriver driver;

    public LoginPage(final WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void login(final String username, final String password) throws InterruptedException {
        wUsername.clear();
        wUsername.sendKeys(username);
        wPassword.clear();
        wPassword.sendKeys(password);
        wSubmitButton.click();
        Thread.sleep(1000);
    }

    // This can only be used after successful logout
    public String getLogoutMessage()  throws NoSuchElementException {
            return driver.findElement(By.id("logout-message")).getText();
    }
}