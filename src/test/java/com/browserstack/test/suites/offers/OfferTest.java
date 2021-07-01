package com.browserstack.test.suites.offers;

import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OfferTest extends TestBase {
    private static final String LOCATION_SCRIPT_FORMAT = "navigator.geolocation.getCurrentPosition = function(success){\n" +
            "    var position = { \"coords\":{\"latitude\":\"%s\",\"longitude\":\"%s\"}};\n" +
            "    success(position);\n" +
            "}";
    private static final String OFFER_LATITUDE = "1";
    private static final String OFFER_LONGITUDE = "103";

    @Test
    public void checkOffersInSingapore() {
        String locationScript = String.format(LOCATION_SCRIPT_FORMAT, OFFER_LATITUDE, OFFER_LONGITUDE);
        ((JavascriptExecutor) getDriver()).executeScript(locationScript);

        getDriver().findElement(By.id("signin")).click();
        getDriver().findElement(By.cssSelector("#username input")).sendKeys("fav_user" + Keys.ENTER); // change
        getDriver().findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        getDriver().findElement(By.id("login-btn")).click();

        getDriver().findElement(By.id("offers")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".spinner")));

        Assert.assertEquals(getDriver().findElements(By.cssSelector(".offer")).size(), 3);
    }

}
