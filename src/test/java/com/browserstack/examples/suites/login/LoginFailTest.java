package com.browserstack.examples.suites.login;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.browserstack.examples.core.ManagedWebDriver;
import com.browserstack.examples.suites.BaseTest;

public class LoginFailTest extends BaseTest {

    @Test
    public void loginSuccess(ManagedWebDriver managedWebDriver) {
        WebDriver webDriver = managedWebDriver.getWebDriver();
        webDriver.findElement(By.id("signin")).click();
        webDriver.findElement(By.cssSelector("#username input")).sendKeys("fav_user" + Keys.ENTER);
        webDriver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        webDriver.findElement(By.id("login-btn")).click();

        Assert.assertEquals(webDriver.findElement(By.className("username")).getText(), "fav");
    }

}
