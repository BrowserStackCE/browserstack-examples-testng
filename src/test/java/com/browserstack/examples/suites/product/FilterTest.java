package com.browserstack.examples.suites.product;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.browserstack.examples.core.ManagedWebDriver;
import com.browserstack.examples.suites.BaseTest;
import com.browserstack.examples.suites.CsvUtil;

public class FilterTest extends BaseTest {

    @Test(dataProvider = "webdriver")
    public void filterLowestToHighestTest(ManagedWebDriver managedWebDriver) {
        WebDriver webDriver = managedWebDriver.getWebDriver();
        Select sortSelect = new Select(webDriver.findElement(By.cssSelector(".sort select")));
        sortSelect.selectByValue("lowestprice");

        WebDriverWait wait = new WebDriverWait(webDriver, 25);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("p.shelf-item__title:first-of-type"), "Pixel 2"));

        List<Integer> values = webDriver.findElements(By.cssSelector(".val > b"))
                .stream()
                .map(WebElement::getText)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        Assertions.assertThat(values).isSorted();
    }

    @Test(dataProvider = "webdriver")
    public void filterVendorTest(ManagedWebDriver managedWebDriver) throws Exception {
        WebDriver webDriver = managedWebDriver.getWebDriver();
        WebDriverWait wait = new WebDriverWait(webDriver, 25);
        webDriver.findElement(By.cssSelector("input[value='Apple'] + span")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".products-found"), "9 Product(s) found."));
        webDriver.findElement(By.cssSelector("input[value='Samsung'] + span")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".products-found"), "16 Product(s) found."));

        List<String> values = webDriver.findElements(By.cssSelector(".shelf-item__title"))
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
        List<String> expectedValues = CsvUtil.readSpecificColumn("src/test/resources/data/products.csv", 2);
        Assertions.assertThat(values).containsExactly(expectedValues.toArray(new String[0]));
    }
}
