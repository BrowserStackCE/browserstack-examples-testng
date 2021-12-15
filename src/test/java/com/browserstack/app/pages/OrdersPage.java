package com.browserstack.app.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class OrdersPage extends BasePage {

    @FindBy(css = ".shipment .a-fixed-right-grid > div")
    private List<WebElement> orderItems;

    public OrdersPage(WebDriver driver) {
        super(driver);
    }

    public int getItemsFromOrder() {
        return wait.until(ExpectedConditions
                .visibilityOfAllElements(orderItems)).size();
    }
}
