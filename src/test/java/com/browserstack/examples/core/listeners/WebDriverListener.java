package com.browserstack.examples.core.listeners;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.browserstack.examples.core.ManagedWebDriver;
import com.browserstack.examples.core.config.DriverType;
import com.browserstack.examples.core.config.WebDriverFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class WebDriverListener extends TestListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverListener.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

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
        markAndCloseWebDriver(webDriver, "failed", testResult.getThrowable().toString());
    }

    private byte[] attachFailedScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }


    private ManagedWebDriver getWebDriverFromParameters(Object[] parameters) {
        Optional<Object> webDriverParam = Arrays.stream(parameters).filter(p -> p instanceof ManagedWebDriver).findFirst();
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
                String script = createExecutorScript(status, reason);
                LOGGER.debug("Script to execute:: {}", script);
                if (StringUtils.isNotEmpty(script)) {
                    ((JavascriptExecutor) webDriver).executeScript(script);
                }
            }
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }

    private String createExecutorScript(String status, String reason) {
        ObjectNode rootNode = objectMapper.createObjectNode();
        ObjectNode argumentsNode = objectMapper.createObjectNode();

        // Read only the first line of the error message
        reason = reason.split("\n")[0];
        // Limit the error message to only 255 characters
        if (reason.length() >= 255) {
            reason = reason.substring(0, 255);
        }

        argumentsNode.put("status", status);
        argumentsNode.put("reason", reason);

        rootNode.put("action", "setSessionStatus");
        rootNode.set("arguments", argumentsNode);
        String executorStr = "";
        try {
            executorStr = objectMapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            throw new Error("Error creating JSON object for Marking tests", e);
        }
        return "browserstack_executor: " + executorStr;
    }

}
