package com.browserstack.test.suites.product;

import com.browserstack.test.suites.TestBase;
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
        Select sortSelect = new Select(driver.get().findElement(By.cssSelector(".sort select")));
        sortSelect.selectByValue("lowestprice");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@class = 'shelf-item__title'][1]"), "Pixel 2"));

        List<Integer> values = getDriver().findElements(By.cssSelector(".val > b")).stream().map(WebElement::getText).map(Integer::parseInt).collect(Collectors.toList());
        Assertions.assertThat(values).isSorted();
    }

    @Test
    public void filterVendorTest() {
        getDriver().findElement(By.xpath("//span[contains(text(), 'Apple')]")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".products-found"), "9 Product(s) found."));
        getDriver().findElement(By.xpath("//span[contains(text(), 'Samsung')]")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".products-found"), "16 Product(s) found."));

        List<String> values = getDriver().findElements(By.cssSelector(".shelf-item__title")).stream().map(WebElement::getText).collect(Collectors.toList());
        Assertions.assertThat(values).containsExactly("iPhone 12",
                "iPhone 12 Mini",
                "iPhone 12 Pro Max",
                "iPhone 12 Pro",
                "iPhone 11",
                "iPhone 11 Pro",
                "iPhone XS",
                "iPhone XR",
                "iPhone XS Max",
                "Galaxy S20",
                "Galaxy S20+",
                "Galaxy S20 Ultra",
                "Galaxy S10",
                "Galaxy S9",
                "Galaxy Note 20",
                "Galaxy Note 20 Ultra");
    }
}
