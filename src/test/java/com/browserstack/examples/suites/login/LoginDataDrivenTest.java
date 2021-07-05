package com.browserstack.examples.suites.login;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.browserstack.examples.core.ManagedWebDriver;
import com.browserstack.examples.suites.BaseTest;

public class LoginDataDrivenTest extends BaseTest {

    @DataProvider(name = "login_error_messages")
    public Iterator<Object[]> data(Method testMethod) {
        Object[][] loginErrorMessages = new Object[][]{
                {"locked_user", "testingisfun99", "Your account has been locked."},
                {"fav_user", "wrongpass", "Invalid Password"},
                {"helloworld", "testingisfun99", "Invalid Username"}
        };
        List<ManagedWebDriver> managedWebDriverList = createManagedWebDrivers(testMethod.getName());

        List<Object[]> testParams = new ArrayList<>();
        for (Object[] lem : loginErrorMessages) {
            for (ManagedWebDriver m : managedWebDriverList) {
                Object[] paramRow = Arrays.copyOf(lem, lem.length + 1);
                paramRow[lem.length] = m;
                testParams.add(paramRow);
            }
        }
        return testParams.iterator();
    }

    @Test(dataProvider = "login_error_messages")
    public void validateErrors(String username, String password, String error, ManagedWebDriver managedWebDriver) {
        WebDriver webDriver = managedWebDriver.getWebDriver();

        webDriver.findElement(By.id("signin")).click();
        webDriver.findElement(By.cssSelector("#username input")).sendKeys(username + Keys.ENTER);
        webDriver.findElement(By.cssSelector("#password input")).sendKeys(password + Keys.ENTER);
        webDriver.findElement(By.id("login-btn")).click();

        Assert.assertEquals(webDriver.findElement(By.className("api-error")).getText(), error);
    }

}
