package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import com.browserstack.test.utils.CsvUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class LoginDataDrivenReadFromCSVTest extends TestBase {

    @DataProvider(name = "login_error_messages")
    public Iterator<String[]> provider() throws Exception {
        return CsvUtil.readAll("src/test/resources/data/users.csv", false);
    }

    @Test(dataProvider = "login_error_messages")
    public void validateErrors(String username, String password, String error) {
        driver.findElement(By.id("signin")).click();
        driver.findElement(By.cssSelector("#username input")).sendKeys(username + Keys.ENTER);
        driver.findElement(By.cssSelector("#password input")).sendKeys(password + Keys.ENTER);
        driver.findElement(By.id("login-btn")).click();

        Assert.assertEquals(driver.findElement(By.className("api-error")).getText(), error);
    }

}
