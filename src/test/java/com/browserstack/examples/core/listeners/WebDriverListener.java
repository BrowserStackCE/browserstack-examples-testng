package com.browserstack.examples.core.listeners;

import java.util.Arrays;
import java.util.Optional;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.browserstack.examples.core.ManagedWebDriver;
import com.browserstack.examples.core.config.DriverType;
import com.browserstack.examples.core.config.WebDriverFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class WebDriverListener extends TestListenerAdapter {

    private static final String TEST_STATUS_SCRIPT = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"%s\", \"reason\": \"%s\"}}";

    @Override
    public void onTestSuccess(ITestResult testResult) {
        super.onTestSuccess(testResult);
        ManagedWebDriver managedWebDriver = getWebDriverFromParameters(testResult.getParameters());
        markAndCloseWebDriver(managedWebDriver, "passed", "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        super.onTestFailure(testResult);
        ManagedWebDriver webDriver = getWebDriverFromParameters(testResult.getParameters());
        markAndCloseWebDriver(webDriver, "failed", testResult.getThrowable().getMessage());
    }

    private byte[] attachFailedScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }


    private ManagedWebDriver getWebDriverFromParameters(Object[] parameters) {
        Optional<Object> webDriverParam = Arrays.stream(parameters).filter(p -> {
            return p instanceof ManagedWebDriver;
        }).findFirst();
        return (ManagedWebDriver) webDriverParam.orElse(null);
    }


    private void markAndCloseWebDriver(ManagedWebDriver managedWebDriver, String status, String reason) {
        if (managedWebDriver == null) {
            return;
        }
        WebDriver webDriver = managedWebDriver.getWebDriver();
        try {
            if (webDriver instanceof RemoteWebDriver
                  && WebDriverFactory.getInstance().getDriverType() == DriverType.cloudDriver) {
                ((JavascriptExecutor) webDriver).executeScript(String.format(TEST_STATUS_SCRIPT, status, reason));
            }
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }

}
