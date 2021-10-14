package com.browserstack.test.suites.e2e;

import com.browserstack.app.pages.ConfirmationPage;
import com.browserstack.app.pages.HomePage;
import com.browserstack.app.pages.OrdersPage;
import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class OrderTest extends TestBase {

    @Test
    public void placeOrder() {
        SoftAssert softly = new SoftAssert();
        ConfirmationPage page = new HomePage(getDriver())
                .navigateToSignIn()
                .loginWith("fav_user", "testingisfun99")
                .addProductToCart("iPhone 11")
                .addProductToCart("iPhone XS Max")
                .addProductToCart("Galaxy S20")
                .getBag().waitForItemsInBag(3)
                .getBag().proceedToCheckout()
                .enterShippingDetails("firstname", "lastname", "address", "state", "12345");
        Assert.assertTrue(page.isConfirmationDisplayed());

        if (isRemoteExecution()) {
            page.downloadPDF();
            softly.assertTrue(downloadedFileExists("confirmation.pdf"));
        }

        OrdersPage ordersPage = page.continueShopping().navigateToOrders();

        softly.assertEquals(ordersPage.getItemsFromOrder(), 3);
        softly.assertAll();
    }

    private boolean downloadedFileExists(String fileName) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        return Boolean.parseBoolean(jse.executeScript("browserstack_executor: {\"action\": \"fileExists\", \"arguments\": {\"fileName\": \"" + fileName + "\"}}").toString());
    }
}