package com.browserstack.examples.suites.login;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.browserstack.examples.suites.BaseTest;
import com.browserstack.webdriver.core.WebDriverFactory;
import com.browserstack.webdriver.testng.LazyInitWebDriverIterator;

import static org.openqa.selenium.Keys.ENTER;

public class LoginDataDrivenTest extends BaseTest {

    @DataProvider(name = "login_error_messages")
    public Iterator<Object[]> data(Method testMethod) {
        List<Object[]> loginErrorMessages = new ArrayList<Object[]>() {{
            add(new Object[]{"locked_user", "testingisfun99", "Your account has been locked."});
            add(new Object[]{"fav_user", "wrongpass", "Invalid Password"});
            add(new Object[]{"helloworld", "testingisfun99", "Invalid Username"});
        }};

        LazyInitWebDriverIterator lazyInitWebDriverIterator = new LazyInitWebDriverIterator(testMethod.getName(),
                                                                                            loginErrorMessages);
        return lazyInitWebDriverIterator;
    }

    @Test(dataProvider = "login_error_messages")
    public void validateErrors(String username, String password, String error, WebDriver webDriver) {
        /* =================== Prepare ================= */
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());

        /* =================== Execute ================= */
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("signin"))).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-2-input"))).sendKeys(username + ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-3-input"))).sendKeys(password + ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("login-btn"))).sendKeys(Keys.RETURN);

        /* =================== Verify ================= */
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .className("api-error")));
        Assert.assertEquals(webDriver.findElement(By.className("api-error")).getText(), error);
    }

}
