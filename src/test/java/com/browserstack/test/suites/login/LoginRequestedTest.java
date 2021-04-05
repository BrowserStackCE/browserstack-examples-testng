package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginRequestedTest extends TestBase {

    @Test
    public void navigateFavoritesLoginRequested() {
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.id("favourites")))).click();
        wait.until(ExpectedConditions.urlContains("favourites"));

        Assert.assertTrue(getDriver().findElement(By.className("login_wrapper")).isDisplayed());
    }
}
