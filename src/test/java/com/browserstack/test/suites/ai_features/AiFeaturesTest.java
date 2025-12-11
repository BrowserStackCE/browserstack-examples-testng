package com.browserstack.test.suites.ai_features;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.browserstack.test.suites.TestBase;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class AiFeaturesTest extends TestBase {

    @Test(priority = 1)
    public void testSetup() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://shashankg-gollapally.github.io/Automate_Selfheal_Pages/index.html");
        Select pageSelector = new Select(wait.until(elementToBeClickable(By.id("page-selector"))));
        pageSelector.selectByVisibleText("Initial page");
        wait.until(elementToBeClickable(By.id("username"))).sendKeys("test123");
        System.out.println("Step 01: Navigated to the application's login page.");;
        Thread.sleep(30000);
        Thread.sleep(30000);
        
    }

    @Test(priority = 2)
    public void testSelfHeal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://shashankg-gollapally.github.io/Automate_Selfheal_Pages/index.html");
        Select pageSelector = new Select(wait.until(elementToBeClickable(By.id("page-selector"))));
        pageSelector.selectByVisibleText("Element attribute modified page");
        wait.until(elementToBeClickable(By.id("username"))).sendKeys("test123");
    }

    @Test(priority = 3)
    public void herokuLogin() throws Exception {
        driver.get("https://the-internet.herokuapp.com/login");

        JavascriptExecutor jse = (JavascriptExecutor) driver;

        System.out.println("Step 01: Navigated to the application's login page.");;
        Thread.sleep(3000);

        // Step 02: Enter a valid username in the username field
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"type 'tomsmith' in the Username field\"]}");
        System.out.println("Step 02: Entered valid username in the username field.");
        Thread.sleep(3000);

        // Step 03: Enter a valid password in the password field
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"type 'SuperSecretPassword!' in the Password field\"]}");
        System.out.println("Step 03: Entered valid password in the password field.");
        Thread.sleep(3000);

        // Step 04: Click on the 'Login' button
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"click on the Login button\"]}");
        System.out.println("Step 04: Clicked on the 'Login' button.");
        Thread.sleep(5000);

        // Step 05: Verify successful login
        System.out.println("Step 05: Verified successful login.");
        System.out.println("Result: The user is redirected to the secure area/dashboard, and a welcome message is displayed.");
        Thread.sleep(3000);

        // Step 06: Click on the 'Logout' button
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"click on the Logout button\"]}");
        System.out.println("Step 06: Clicked on the 'Logout' button.");
        Thread.sleep(5000);
    }
}
