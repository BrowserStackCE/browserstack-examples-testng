package com.browserstack.examples.suites.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.browserstack.examples.suites.BaseTest;

public class LoginRequestedTest extends BaseTest {

    @Test(dataProvider = "webdriver")
    public void navigateFavoritesLoginRequested(WebDriver webDriver) {
        WebDriverWait wait = new WebDriverWait(webDriver, 25);
        wait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("favourites")))).click();
        wait.until(ExpectedConditions.urlContains("favourites"));

        Assert.assertTrue(webDriver.findElement(By.className("login_wrapper")).isDisplayed());
    }
}
