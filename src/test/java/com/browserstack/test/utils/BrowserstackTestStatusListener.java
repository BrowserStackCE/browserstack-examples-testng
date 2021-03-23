package com.browserstack.test.utils;

import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class BrowserstackTestStatusListener extends TestListenerAdapter {

    private void markTestStatus(String status, String reason, WebDriver driver) { 
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status + "\", \"reason\": \"" + reason + "\"}}");
        } catch (Exception e) {
            System.out.print("Error executing javascript");
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Object currentClass = result.getInstance();
        WebDriver driver = ((TestBase) currentClass).getDriver();
        markTestStatus("passed", "", driver);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Object currentClass = result.getInstance();
        WebDriver driver = ((TestBase) currentClass).getDriver();
        markTestStatus("failed", result.getThrowable().getMessage(), driver);
    }

}
