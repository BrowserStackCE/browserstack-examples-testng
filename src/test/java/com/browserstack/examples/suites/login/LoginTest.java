package com.browserstack.examples.suites.login;

import com.browserstack.webdriver.core.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.browserstack.examples.suites.BaseTest;

import static org.openqa.selenium.Keys.ENTER;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "webdriver")
    public void loginLockedUser(WebDriver webDriver) {
        /* =================== Prepare ================= */
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());

        /* =================== Execute ================= */
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("signin"))).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-2-input"))).sendKeys("locked_user" + ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-3-input"))).sendKeys("testingisfun99" + ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("login-btn"))).sendKeys(Keys.RETURN);

        /* =================== Verify ================= */
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .className("api-error")));
        Assert.assertEquals(webDriver.findElement(By.className("api-error")).getText(), "Your account has been locked.");
    }

    @Test(dataProvider = "webdriver")
    public void loginSuccess(WebDriver webDriver) {
        /* =================== Prepare ================= */
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());

        /* =================== Execute ================= */
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("signin"))).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-2-input"))).sendKeys("fav_user" + Keys.ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-3-input"))).sendKeys("testingisfun99" + Keys.ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("login-btn"))).sendKeys(Keys.RETURN);

        /* =================== Verify ================= */
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .className("username")));
        Assert.assertEquals(webDriver.findElement(By.className("username")).getText(), "fav_user");
    }

    @Test(dataProvider = "webdriver")
    public void loginFail(WebDriver webDriver) {
        /* =================== Prepare ================= */
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());

        /* =================== Execute ================= */
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("signin"))).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-2-input"))).sendKeys("fav_user" + Keys.ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-3-input"))).sendKeys("wrong_pass" + Keys.ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("login-btn"))).sendKeys(Keys.RETURN);

        /* =================== Verify ================= */
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .className("api-error")));
        Assert.assertEquals(webDriver.findElement(By.className("api-error")).getText(), "Invalid Password");
    }

}
