package com.browserstack.test.suites.offers;

import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class OfferTest extends TestBase {

    @Override
    public String getGeolocation() {
        return "IN";
    }

    @Test
    public void checkOffersInIndia() {
        getDriver().findElement(By.id("signin")).click();
        getDriver().findElement(By.cssSelector("#username input")).sendKeys("fav_user" + Keys.ENTER); // change
        getDriver().findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        getDriver().findElement(By.id("login-btn")).click();

        getDriver().findElement(By.id("offers")).click();
        wait.until(ExpectedConditions.urlContains("offers"));
    }

}
