package com.browserstack.examples.suites.user;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.browserstack.examples.core.ManagedWebDriver;
import com.browserstack.examples.suites.BaseTest;

public class UserTest extends BaseTest {

    @Test(dataProvider = "webdriver")
    public void loginImagesNotLoading(ManagedWebDriver managedWebDriver) {
        WebDriver webDriver = managedWebDriver.getWebDriver();
        webDriver.findElement(By.id("signin")).click();
        webDriver.findElement(By.cssSelector("#username input")).sendKeys("image_not_loading_user" + Keys.ENTER);
        webDriver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        webDriver.findElement(By.id("login-btn")).click();

        List<WebElement> imageSrc = webDriver.findElements(By.cssSelector(".shelf-item__thumb img"))
                .stream()
                .filter(image -> !image.getAttribute("src").equals(""))
                .collect(Collectors.toList());
        Assert.assertTrue(!imageSrc.isEmpty());
    }

    @Test(dataProvider = "webdriver")
    public void loginAndCheckExistingOrders(ManagedWebDriver managedWebDriver) {
        WebDriver webDriver = managedWebDriver.getWebDriver();
        webDriver.findElement(By.id("signin")).click();
        webDriver.findElement(By.cssSelector("#username input")).sendKeys("existing_orders_user" + Keys.ENTER);
        webDriver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        webDriver.findElement(By.id("login-btn")).click();

        webDriver.findElement(By.id("orders")).click();
        WebDriverWait wait = new WebDriverWait(webDriver, 25);
        wait.until(ExpectedConditions.urlContains("orders"));

        Assertions.assertThat(webDriver.findElements(By.cssSelector(".order")).size()).isGreaterThanOrEqualTo(5);
    }

    @Test(dataProvider = "webdriver")
    public void addToFavourites(ManagedWebDriver managedWebDriver) {
        WebDriver webDriver = managedWebDriver.getWebDriver();
        webDriver.findElement(By.id("signin")).click();
        webDriver.findElement(By.cssSelector("#username input")).sendKeys("demouser" + Keys.ENTER);
        webDriver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        webDriver.findElement(By.id("login-btn")).click();

        webDriver.findElement(By.cssSelector("div[id='1'] > div > button")).click();

        webDriver.findElement(By.id("favourites")).click();
        WebDriverWait wait = new WebDriverWait(webDriver, 25);
        wait.until(ExpectedConditions.urlContains("favourites"));

        Assertions.assertThat(webDriver.findElements(By.cssSelector("p.shelf-item__title"))
                .stream().map(WebElement::getText)).contains("iPhone 12");
    }

}
