package com.browserstack.app.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    @FindBy(css = "#username input")
    private WebElement usernameInput;

    @FindBy(css = "#password input")
    private WebElement passwordInput;

    @FindBy(id = "login-btn")
    private WebElement logInButton;

    @FindBy(css = ".username")
    private WebElement signedInUsername;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public HomePage loginWith(String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(usernameInput)).sendKeys(username + Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password + Keys.ENTER);
        logInButton.click();
        return new HomePage(driver);
    }

    public String getSignedInUsername() {
        return signedInUsername.getText();
    }
}
