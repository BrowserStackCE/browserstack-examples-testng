package com.browserstack.examples.suites.login;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.browserstack.examples.suites.BaseTest;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "webdriver")
    public void loginLockedUser(WebDriver webDriver) {
        webDriver.findElement(By.id("signin")).click();
        webDriver.findElement(By.cssSelector("#username input")).sendKeys("locked_user" + Keys.ENTER);
        webDriver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        webDriver.findElement(By.id("login-btn")).click();

        Assert.assertEquals(webDriver.findElement(By.className("api-error")).getText(), "Your account has been locked.");
    }

    @Test(dataProvider = "webdriver")
    public void loginSuccess(WebDriver webDriver) {
        webDriver.findElement(By.id("signin")).click();
        webDriver.findElement(By.cssSelector("#username input")).sendKeys("fav_user" + Keys.ENTER);
        webDriver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        webDriver.findElement(By.id("login-btn")).click();

        Assert.assertEquals(webDriver.findElement(By.className("username")).getText(), "fav_user");
    }

    @Test(dataProvider = "webdriver")
    public void loginFail(WebDriver webDriver) {
        webDriver.findElement(By.id("signin")).click();
        webDriver.findElement(By.cssSelector("#username input")).sendKeys("fav_user" + Keys.ENTER);
        webDriver.findElement(By.cssSelector("#password input")).sendKeys("wrongpass" + Keys.ENTER);
        webDriver.findElement(By.id("login-btn")).click();

        Assert.assertEquals(webDriver.findElement(By.className("api-error")).getText(), "Invalid Password");
    }

}
