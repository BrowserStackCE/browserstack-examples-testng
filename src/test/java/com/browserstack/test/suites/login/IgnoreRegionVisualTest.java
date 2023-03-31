package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class IgnoreRegionVisualTest extends TestBase {

    @Test
    public void ignoreLogo() {
        driver.findElement(By.id("signin")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#username input")));

        JavascriptExecutor ex = ((JavascriptExecutor) driver);
        String percyCSS = "svg {visibility: hidden}";

        percy.snapshot("Check Loginpage with ignored logo", null, 1024, false, percyCSS);
    }

}
