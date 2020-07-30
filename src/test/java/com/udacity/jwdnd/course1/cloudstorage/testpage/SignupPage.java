package com.udacity.jwdnd.course1.cloudstorage.testpage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    private final WebDriverWait wait;

    public SignupPage(final WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("submit-button")));
        PageFactory.initElements(driver, this);
    }

    public void signup(final String firstName, final String lastName, final String username, final String password) {
        wait.until(ExpectedConditions.elementToBeClickable(wSubmitButton));
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

    // This can only be used after successful signup
    public boolean goLogin() {
        try {
            WebElement wLogin = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-link")));
            driver.get(wLogin.getAttribute("href"));
            return true;
        } catch(NoSuchElementException e) {
            return false;
        }        
    }

}