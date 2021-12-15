package com.browserstack.examples.suites.e2e;

import com.browserstack.webdriver.core.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.browserstack.app.pages.ConfirmationPage;
import com.browserstack.app.pages.HomePage;
import com.browserstack.app.pages.OrdersPage;
import com.browserstack.examples.suites.BaseTest;
import com.browserstack.webdriver.testng.ManagedWebDriver;

public class OrderTest extends BaseTest {

    @Test(dataProvider = "managedwebdriver")
    public void placeOrder(ManagedWebDriver managedWebDriver) {
        WebDriver webDriver = managedWebDriver.getWebDriver();
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());
        ConfirmationPage page = new HomePage(webDriver)
                .navigateToSignIn()
                .loginWith("fav_user", "testingisfun99")
                .addProductToCart("iPhone 11")
                .addProductToCart("iPhone XS Max")
                .addProductToCart("Galaxy S20")
                .getBag().waitForItemsInBag(3)
                .getBag().proceedToCheckout()
                .enterShippingDetails("firstname", "lastname", "address", "state", "12345");
        Assert.assertTrue(page.isConfirmationDisplayed());

        OrdersPage ordersPage = page.continueShopping().navigateToOrders();

        Assert.assertEquals(ordersPage.getItemsFromOrder(), 3);
    }
}
