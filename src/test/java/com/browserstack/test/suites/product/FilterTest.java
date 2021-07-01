package com.browserstack.test.suites.product;

import com.browserstack.test.suites.TestBase;
import com.browserstack.test.utils.CsvUtil;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class FilterTest extends TestBase {

    @Test
    public void filterLowestToHighestTest() {
        Select sortSelect = new Select(getDriver().findElement(By.cssSelector(".sort select")));
        sortSelect.selectByValue("lowestprice");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("p.shelf-item__title:first-of-type"), "Pixel 2"));

        List<Integer> values = getDriver().findElements(By.cssSelector(".val > b"))
                .stream()
                .map(WebElement::getText)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        Assertions.assertThat(values).isSorted();
    }

    @Test
    public void filterVendorTest() throws Exception {
        getDriver().findElement(By.cssSelector("input[value='Apple'] + span")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".products-found"), "9 Product(s) found."));
        getDriver().findElement(By.cssSelector("input[value='Samsung'] + span")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".products-found"), "16 Product(s) found."));

        List<String> values = getDriver().findElements(By.cssSelector(".shelf-item__title"))
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
        List<String> expectedValues = CsvUtil.readSpecificColumn("src/test/resources/data/products.csv", 2);
        Assertions.assertThat(values).containsExactly(expectedValues.toArray(new String[0]));
    }
}
