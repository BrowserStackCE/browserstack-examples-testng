package com.browserstack.app.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ConfirmationPage extends BasePage {

    @FindBy(css = ".continueButtonContainer button")
    private WebElement continueShoppingButton;

    private By confirmationMessage = By.id("confirmation-message");

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public boolean isConfirmationDisplayed() {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(confirmationMessage, "Your Order has been successfully placed."));
    }

    public HomePage continueShopping() {
        continueShoppingButton.click();
        return new HomePage(driver);
    }
}
