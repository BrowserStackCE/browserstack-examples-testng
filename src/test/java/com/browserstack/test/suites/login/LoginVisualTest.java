package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginVisualTest extends TestBase {

    @Test
    public void loginLockedUser() {

        percy.snapshot("Check Homepage");
        getDriver().findElement(By.id("signin")).click();
        percy.snapshot("Check Signin page");
        getDriver().findElement(By.cssSelector("#username input")).sendKeys("locked_user" + Keys.ENTER);
        getDriver().findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        getDriver().findElement(By.id("login-btn")).click();
        percy.snapshot("Check Login result");

        Assert.assertEquals(getDriver().findElement(By.className("api-error")).getText(), "Your account has been locked.");
    }
}
