package com.browserstack.app.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 5, 1000);
        PageFactory.initElements(driver, this);
    }

    protected void waitFortextToBePresentInElement(WebElement element, String text) {
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    protected void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

}
