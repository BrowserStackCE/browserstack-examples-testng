package com.browserstack.examples.suites.login;

import java.io.FileReader;
import java.lang.reflect.Method;
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
import com.opencsv.CSVReader;
import com.browserstack.webdriver.core.WebDriverFactory;
import com.browserstack.webdriver.testng.LazyInitWebDriverIterator;

import static org.openqa.selenium.Keys.ENTER;

public class LoginDataDrivenReadFromCSVTest extends BaseTest {

    @DataProvider(name = "login_error_messages_with_webdriver")
    public Iterator<Object[]> provider(Method testMethod) throws Exception {
        String userDataPath = "classpath:data/users.csv";
        CSVReader csvReader = new CSVReader(new FileReader(userDataPath));
        List<String[]> userDataLines = csvReader.readAll();
        List<? extends Object[]> methodParams = userDataLines;
        LazyInitWebDriverIterator lazyInitWebDriverIterator = new LazyInitWebDriverIterator(testMethod.getName(),
                                                                                            (List<Object[]>) methodParams);
        return lazyInitWebDriverIterator;
    }

    @Test(dataProvider = "login_error_messages_with_webdriver")
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
