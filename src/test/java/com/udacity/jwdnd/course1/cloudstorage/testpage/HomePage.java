package com.udacity.jwdnd.course1.cloudstorage.testpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    //@FindBy(id="logout-button")
    //private WebElement wLogoutButton;

    @FindBy(id="logout-form")
    private WebElement wLogoutForm;

    //private final WebDriver driver;

    public HomePage(final WebDriver driver) {
        PageFactory.initElements(driver, this);
        //this.driver = driver;
    }

    public void logout() {
        //wLogoutButton.click();
        wLogoutForm.submit();
    }

}