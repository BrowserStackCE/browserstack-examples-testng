package com.browserstack.examples.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.browserstack.examples.config.WebDriverFactory;
import com.browserstack.examples.listeners.WebDriverListener;
import com.browserstack.examples.providers.ManagedWebDriver;
import com.browserstack.examples.providers.WebDriverDataProvider;

/**
 * Test the filter capability on the BrowserStack Demo application.
 */
@Listeners({WebDriverListener.class})
public class FilterTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterTests.class);

    @Test(dataProvider = "webDriverProvider", dataProviderClass = WebDriverDataProvider.class)
    public void testSelectingAppleFilterDisplaysNoSamsungDevices(ManagedWebDriver managedWebDriver) throws Exception {
        /* =================== Prepare ================= */
        WebDriver webDriver = managedWebDriver.getWebDriver();
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

    @Test(dataProvider = "webDriverProvider", dataProviderClass = WebDriverDataProvider.class)
    public void testSelectingSamsungFilterDisplaysNoAppleDevices(ManagedWebDriver managedWebDriver) throws Exception {
        /* =================== Prepare ================= */
        WebDriver webDriver = managedWebDriver.getWebDriver();
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
