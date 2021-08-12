package com.browserstack.examples.suites.filters;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.browserstack.examples.suites.BaseTest;
import com.browserstack.webdriver.core.WebDriverFactory;

/**
 * Test the filter capability on the BrowserStack Demo application.
 */
public class FilterTests extends BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterTests.class);

    @Test(dataProvider = "webdriver")
    public void testSelectingAppleFilterDisplaysNoSamsungDevices(WebDriver webDriver) throws Exception {
        /* =================== Prepare ================= */
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());

        /* =================== Execute ================= */
        wait.until(ExpectedConditions
                     .visibilityOfElementLocated(By
                                                   .cssSelector(".filters-available-size:nth-child(2) .checkmark"))).click();
        wait.until(waitWebDriver -> waitWebDriver.findElements(By.cssSelector(".spinner")).isEmpty());
        List<WebElement> appleDeviceName = webDriver.findElements(By.cssSelector(".shelf-item__title"));

        /* =================== Verify ================= */
        long samsungDeviceCount = appleDeviceName.stream().filter(e -> e.getText().contains("Galaxy")).count();
        Assert.assertEquals(samsungDeviceCount, 0);
    }

    @Test(dataProvider = "webdriver")
    public void testSelectingSamsungFilterDisplaysNoAppleDevices(WebDriver webDriver) throws Exception {
        /* =================== Prepare ================= */
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());

        /* =================== Execute ================= */
        wait.until(ExpectedConditions
                     .visibilityOfElementLocated(By
                                                   .cssSelector(".filters-available-size:nth-child(3) .checkmark"))).click();
        wait.until(waitWebDriver -> waitWebDriver.findElements(By.cssSelector(".spinner")).isEmpty());
        List<WebElement> samsungDeviceName = webDriver.findElements(By.cssSelector(".shelf-item__title"));

        /* =================== Verify ================= */
        long appleDeviceCount = samsungDeviceName.stream().filter(e -> e.getText().contains("iPhone")).count();
        Assert.assertEquals(appleDeviceCount, 0);
    }

}
