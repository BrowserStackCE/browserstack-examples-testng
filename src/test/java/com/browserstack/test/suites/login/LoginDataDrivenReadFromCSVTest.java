package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoginDataDrivenReadFromCSVTest extends TestBase {

    @DataProvider(name = "errors")
    public Iterator<Object[]> provider() throws IOException {
        return readCsv("data/users.csv");
    }

    private Iterator<Object[]> readCsv(String filename) throws IOException {
        List<Object[]> testCases = new ArrayList<>();
        String[] data = null;
        String line = "";

        BufferedReader br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource(filename).getFile()));
        while ((line = br.readLine()) != null) {
            data = line.split(",");
            testCases.add(data);
        }
        br.close();
        return testCases.iterator();
    }

    @Test(dataProvider = "errors")
    public void validateErrors(String username, String password, String error) {
        getDriver().findElement(By.id("signin")).click();
        getDriver().findElement(By.cssSelector("#username input")).sendKeys(username + Keys.ENTER);
        getDriver().findElement(By.cssSelector("#password input")).sendKeys(password + Keys.ENTER);
        getDriver().findElement(By.id("login-btn")).click();

        Assert.assertEquals(getDriver().findElement(By.className("api-error")).getText(), error);
    }

}
