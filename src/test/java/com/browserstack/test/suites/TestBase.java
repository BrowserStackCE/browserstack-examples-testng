package com.browserstack.test.suites;

import com.browserstack.test.utils.DriverUtil;
import com.browserstack.test.utils.ScreenshotListener;
import io.percy.selenium.Percy;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Listeners({ ScreenshotListener.class })
public class TestBase {

    public WebDriver driver;
    protected WebDriverWait wait;
    public static Percy percy;

    public boolean isOnPremExecution() {
        return StringUtils.equalsIgnoreCase(System.getProperty("on-prem"), "true");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        if (isOnPremExecution()) {
            DriverUtil.setDriverPathVariable();
            driver = new ChromeDriver();
        } else {
            MutableCapabilities capabilities = new MutableCapabilities();
            HashMap<String, String> bstackOptions = new HashMap<>();
            bstackOptions.putIfAbsent("source", "testng-java:sample-sdk:v1.0");
            capabilities.setCapability("bstack:options", bstackOptions);
            driver = new RemoteWebDriver(new URL("https://hub.browserstack.com/wd/hub"), capabilities);
        }
        percy = new Percy(driver);
        if (StringUtils.equalsIgnoreCase(System.getProperty("browserstack-local"),"true")) {
            driver.get("http://localhost:3000");
            driver.manage().window().maximize();
        } else {
            driver.get("https://bstackdemo.com");
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
