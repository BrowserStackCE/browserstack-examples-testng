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
    public void loginImagesNotLoading() {
        getDriver().findElement(By.id("signin")).click();
        getDriver().findElement(By.cssSelector("#username input")).sendKeys("image_not_loading_user" + Keys.ENTER);
        getDriver().findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        getDriver().findElement(By.id("login-btn")).click();

        List<WebElement> imageSrc = getDriver().findElements(By.cssSelector(".shelf-item__thumb img")).stream().filter(image -> !image.getAttribute("src").equals("")).collect(Collectors.toList());
        Assert.assertTrue(imageSrc.isEmpty());
    }

    @Test
    public void loginAndCheckExistingOrders() {
        getDriver().findElement(By.id("signin")).click();
        getDriver().findElement(By.cssSelector("#username input")).sendKeys("existing_orders_user" + Keys.ENTER);
        getDriver().findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        getDriver().findElement(By.id("login-btn")).click();

        getDriver().findElement(By.id("orders")).click();
        wait.until(ExpectedConditions.urlContains("orders"));

        Assertions.assertThat(getDriver().findElements(By.cssSelector(".order")).size()).isGreaterThanOrEqualTo(5);
    }

    @Test
    public void addToFavourites() {
        getDriver().findElement(By.id("signin")).click();
        getDriver().findElement(By.cssSelector("#username input")).sendKeys("existing_orders_user" + Keys.ENTER);
        getDriver().findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        getDriver().findElement(By.id("login-btn")).click();

        getDriver().findElement(By.xpath("//p[text() = 'iPhone 12']/../div/button")).click();

        getDriver().findElement(By.id("favourites")).click();
        wait.until(ExpectedConditions.urlContains("favourites"));

        Assertions.assertThat(getDriver().findElements(By.cssSelector("p.shelf-item__title"))
                .stream().map(WebElement::getText)).contains("iPhone 12");
    }

}
