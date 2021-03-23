package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {

    @Test
    public void loginLockedUser() {
        getDriver().findElement(By.id("signin")).click();
        getDriver().findElement(By.cssSelector("#username input")).sendKeys("locked_user" + Keys.ENTER);
        getDriver().findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        getDriver().findElement(By.id("login-btn")).click();

        Assert.assertEquals(getDriver().findElement(By.className("api-error")).getText(), "Your account has been locked.");
    }

    @Test
    public void loginSuccess() {
        getDriver().findElement(By.id("signin")).click();
        getDriver().findElement(By.cssSelector("#username input")).sendKeys("fav_user" + Keys.ENTER);
        getDriver().findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        getDriver().findElement(By.id("login-btn")).click();

        Assert.assertEquals(getDriver().findElement(By.className("username")).getText(), "fav_user");
    }

    @Test
    public void loginFail() {
        getDriver().findElement(By.id("signin")).click();
        getDriver().findElement(By.cssSelector("#username input")).sendKeys("fav_user" + Keys.ENTER);
        getDriver().findElement(By.cssSelector("#password input")).sendKeys("wrongpass" + Keys.ENTER);
        getDriver().findElement(By.id("login-btn")).click();

        Assert.assertEquals(getDriver().findElement(By.className("api-error")).getText(), "Invalid Password");
    }

}
