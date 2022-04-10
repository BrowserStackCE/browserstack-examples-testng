package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginVisualTest extends TestBase {

    private boolean changeCSS = false;

    @Test
    public void loginLockedUser() {
        JavascriptExecutor ex = ((JavascriptExecutor) getDriver());

        percy.snapshot("Check Homepage");
        getDriver().findElement(By.id("signin")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#username input")));

        if (changeCSS) {
            ex.executeScript("document.getElementById('login-btn').style.backgroundColor = '#FFA500';");
            ex.executeScript("document.getElementById('login-btn').style.width = '60%';");
        }
        percy.snapshot("Check Signin page");
        getDriver().findElement(By.cssSelector("#username input")).sendKeys("locked_user" + Keys.ENTER);
        getDriver().findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        getDriver().findElement(By.id("login-btn")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("api-error")));
        if (changeCSS) {
            ex.executeScript("document.getElementsByClassName('api-error')[0].style.fontSize = '200%';");
        }
        percy.snapshot("Check Login result");
        Assert.assertEquals(getDriver().findElement(By.className("api-error")).getText(), "Your account has been locked.");
    }

}
