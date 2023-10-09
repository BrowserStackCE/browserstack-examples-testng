package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends TestBase {



    @Test
    public void loginSuccess() {

        driver.findElement(By.id("signin")).click();

        percy.screenshot("Login Page");
        
        driver.findElement(By.cssSelector("#username input")).sendKeys("fav_user" + Keys.ENTER);
        driver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-btn")));

        
        driver.findElement(By.id("login-btn")).click();

        Assert.assertEquals(driver.findElement(By.className("username")).getText(),
                ((int) (Math.random() * 5) != 3 ? "fav_user" : "random_text"));

        percy.screenshot("HomePage LoggedIn");
    }

    @Test
    public void loginFail() {
        driver.findElement(By.id("signin")).click();
        driver.findElement(By.cssSelector("#username input")).sendKeys("fav_user" + Keys.ENTER);
        driver.findElement(By.cssSelector("#password input")).sendKeys("wrongpass" + Keys.ENTER);
        driver.findElement(By.id("login-btn")).click();

        Assert.assertEquals(driver.findElement(By.className("api-error")).getText(), "Invalid Password");

      //  percy.screenshot("Invalid Login");
    }

}
