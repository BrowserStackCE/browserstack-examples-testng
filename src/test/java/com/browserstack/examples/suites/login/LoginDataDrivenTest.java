package com.browserstack.examples.suites.login;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.browserstack.examples.core.LazyInitWebDriverIterator;
import com.browserstack.examples.core.config.WebDriverFactory;
import com.browserstack.examples.suites.BaseTest;

public class LoginDataDrivenTest extends BaseTest {

    @DataProvider(name = "login_error_messages")
    public Iterator<Object[]> data(Method testMethod) {
        List<Object[]> loginErrorMessages = new ArrayList<Object[]>() {{
            add(new Object[]{"locked_user", "testingisfun99", "Your account has been locked."});
            add(new Object[]{"fav_user", "wrongpass", "Invalid Password"});
            add(new Object[]{"helloworld", "testingisfun99", "Invalid Username"});
        }};

        LazyInitWebDriverIterator lazyInitWebDriverIterator = new LazyInitWebDriverIterator(testMethod.getName(),
                                                                                            WebDriverFactory.getInstance().getPlatforms(),
                                                                                            loginErrorMessages);
        return lazyInitWebDriverIterator;
    }

    @Test(dataProvider = "login_error_messages")
    public void validateErrors(String username, String password, String error, WebDriver webDriver) {

        webDriver.findElement(By.id("signin")).click();
        webDriver.findElement(By.cssSelector("#username input")).sendKeys(username + Keys.ENTER);
        webDriver.findElement(By.cssSelector("#password input")).sendKeys(password + Keys.ENTER);
        webDriver.findElement(By.id("login-btn")).click();

        Assert.assertEquals(webDriver.findElement(By.className("api-error")).getText(), error);
    }

}
