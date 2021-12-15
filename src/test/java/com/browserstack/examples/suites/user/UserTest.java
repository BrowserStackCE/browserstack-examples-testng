package com.browserstack.examples.suites.user;

import java.util.List;
import java.util.stream.Collectors;

import com.browserstack.webdriver.core.WebDriverFactory;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.browserstack.examples.suites.BaseTest;

public class UserTest extends BaseTest {

    @Test(dataProvider = "webdriver")
    public void loginImagesNotLoading(WebDriver webDriver) {
        /* =================== Prepare ================= */
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());

        /* =================== Execute ================= */
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("signin"))).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-2-input"))).sendKeys("image_not_loading_user" + Keys.ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-3-input"))).sendKeys("testingisfun99" + Keys.ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("login-btn"))).sendKeys(Keys.RETURN);
        List<WebElement> imageSrc = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.
                cssSelector(".shelf-item__thumb img")))
                .stream()
                .filter(image -> !image.getAttribute("src").equals(""))
                .collect(Collectors.toList());

        /* =================== Verify ================= */
        Assert.assertTrue(!imageSrc.isEmpty());
    }

    @Test(dataProvider = "webdriver")
    public void loginAndCheckExistingOrders(WebDriver webDriver) {
        /* =================== Prepare ================= */
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());

        /* =================== Execute ================= */
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("signin"))).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-2-input"))).sendKeys("existing_orders_user" + Keys.ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-3-input"))).sendKeys("testingisfun99" + Keys.ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("login-btn"))).sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("orders"))).click();
        wait.until(ExpectedConditions.urlContains("orders"));

        /* =================== Verify ================= */
        Assertions.assertThat(wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.cssSelector(".order"))).size()).isGreaterThanOrEqualTo(5);
    }

    @Test(dataProvider = "webdriver")
    public void addToFavourites(WebDriver webDriver) {
        /* =================== Prepare ================= */
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());

        /* =================== Execute ================= */
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("signin"))).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-2-input"))).sendKeys("demouser" + Keys.ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("react-select-3-input"))).sendKeys("testingisfun99" + Keys.ENTER);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .id("login-btn"))).sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("div[id='1'] > div > button"))).click();
        webDriver.findElement(By.id("favourites")).click();
        wait.until(ExpectedConditions.urlContains("favourites"));

        /* =================== Verify ================= */
        Assertions.assertThat(webDriver.findElements(By.cssSelector("p.shelf-item__title"))
                .stream().map(WebElement::getText)).contains("iPhone 12");
    }

}
