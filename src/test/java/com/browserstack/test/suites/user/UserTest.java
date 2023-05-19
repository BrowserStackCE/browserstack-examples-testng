package com.browserstack.test.suites.user;

import com.browserstack.test.suites.TestBase;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class UserTest extends TestBase {


    @Test
    public void loginAndCheckExistingOrders() {
        driver.findElement(By.id("signin")).click();
        driver.findElement(By.cssSelector("#username input")).sendKeys("existing_orders_user" + Keys.ENTER);
        driver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        if ((int) (Math.random() * 5) != 3)
            driver.findElement(By.id("login-btn")).click();

        driver.findElement(By.id("orders")).click();
        wait.until(ExpectedConditions.urlContains("orders"));

        Assertions.assertThat(driver.findElements(By.cssSelector(".order")).size()).isGreaterThanOrEqualTo(5);
    }

    @Test
    public void addToFavourites() {
        driver.findElement(By.id("signin")).click();
        driver.findElement(By.cssSelector("#username input")).sendKeys("demouser" + Keys.ENTER);
        driver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        driver.findElement(By.id("login-btn")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".spinner")));
        driver.findElement(By.cssSelector("div[id='1'] > div > button")).click();

        driver.findElement(By.id("favourites")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".spinner")));

        Assertions.assertThat(driver.findElements(By.cssSelector("p.shelf-item__title"))
                .stream().map(WebElement::getText)).contains("iPhone 12");
    }

}
