package com.browserstack.app.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    @FindBy(id = "signin")
    private WebElement signInLink;

    @FindBy(css = "a#orders")
    private WebElement ordersLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage navigateToSignIn() {
        signInLink.click();
        return new LoginPage(driver);
    }

    public HomePage addProductToCart(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text() = '" + productName + "']/../div[@class = 'shelf-item__buy-btn']")));
        waitAndClick(driver.findElement(By.xpath("//p[text() = '" + productName + "']/../div[@class = 'shelf-item__buy-btn']")));
        getBag().close();
        return this;
    }

    public OrdersPage navigateToOrders() {
        ordersLink.click();
        return new OrdersPage(driver);
    }

    public Bag getBag() {
        return new Bag(driver);
    }
}
