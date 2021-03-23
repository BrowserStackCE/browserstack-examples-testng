package com.browserstack.app.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(id = "signin")
    private WebElement signInLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage navigateToSignIn() {
        signInLink.click();
        return new LoginPage(driver);
    }

    public HomePage addProductToCart(String productName) {
        waitAndClick(driver.findElement(By.xpath("//p[text() = '" + productName + "']/../div[@class = 'shelf-item__buy-btn']")));
        getBag().close();
        return this;
    }

    public OrdersPage navigateToOrders() {
        driver.findElement(By.cssSelector("a#orders")).click();
        return new OrdersPage(driver);
    }

    public Bag getBag() {
        return new Bag(driver);
    }
}
