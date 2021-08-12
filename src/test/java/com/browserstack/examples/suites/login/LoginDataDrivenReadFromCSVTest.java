package com.browserstack.examples.suites.login;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.browserstack.examples.suites.BaseTest;
import com.opencsv.CSVReader;
import com.browserstack.webdriver.core.WebDriverFactory;
import com.browserstack.webdriver.testng.LazyInitWebDriverIterator;

public class LoginDataDrivenReadFromCSVTest extends BaseTest {

    @DataProvider(name = "login_error_messages_with_webdriver")
    public Iterator<Object[]> provider(Method testMethod) throws Exception {
        String userDataPath = "classpath:data/users.csv";
        CSVReader csvReader = new CSVReader(new FileReader(userDataPath));
        List<String[]> userDataLines = csvReader.readAll();
        List<? extends Object[]> methodParams = userDataLines;
        LazyInitWebDriverIterator lazyInitWebDriverIterator = new LazyInitWebDriverIterator(testMethod.getName(),
                                                                                            WebDriverFactory.getInstance().getPlatforms(),
                                                                                            (List<Object[]>) methodParams);
        return lazyInitWebDriverIterator;
    }

    @Test(dataProvider = "login_error_messages_with_webdriver")
    public void validateErrors(String username, String password, String error, WebDriver webDriver) {
        webDriver.findElement(By.id("signin")).click();
        webDriver.findElement(By.cssSelector("#username input")).sendKeys(username + Keys.ENTER);
        webDriver.findElement(By.cssSelector("#password input")).sendKeys(password + Keys.ENTER);
        webDriver.findElement(By.id("login-btn")).click();

        Assert.assertEquals(webDriver.findElement(By.className("api-error")).getText(), error);
    }

}
