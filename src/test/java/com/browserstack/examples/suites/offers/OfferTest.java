package com.browserstack.examples.suites.offers;

import com.browserstack.webdriver.core.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.browserstack.examples.suites.BaseTest;

public class OfferTest extends BaseTest {

    private static final String LOCATION_SCRIPT_FORMAT = "navigator.geolocation.getCurrentPosition = function(success){\n" +
            "    var position = { \"coords\":{\"latitude\":\"%s\",\"longitude\":\"%s\"}};\n" +
            "    success(position);\n" +
            "}";
    private static final String OFFER_LATITUDE = "1";
    private static final String OFFER_LONGITUDE = "103";

    @Test(dataProvider = "webdriver")
    public void checkOffersInSingapore(WebDriver webDriver) {
        WebDriverWait wait = new WebDriverWait(webDriver, 25);
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());
        String locationScript = String.format(LOCATION_SCRIPT_FORMAT, OFFER_LATITUDE, OFFER_LONGITUDE);
        ((JavascriptExecutor) webDriver).executeScript(locationScript);

        webDriver.findElement(By.id("signin")).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .cssSelector("#username input"))).sendKeys("fav_user" + Keys.ENTER); // change
        webDriver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        webDriver.findElement(By.id("login-btn")).click();

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("offers"))).click();
        wait.until(ExpectedConditions.urlContains("offers"));

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".offer")));
        Assert.assertEquals(webDriver.findElements(By.cssSelector(".offer")).size(), 3);
    }

}
