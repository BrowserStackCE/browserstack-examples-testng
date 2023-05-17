package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import com.browserstack.app.pages.ConfirmationPage;
import com.browserstack.app.pages.HomePage;
import com.browserstack.app.pages.OrdersPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginVisualTest extends TestBase {

    @Test
    public void loginLockedUser() {
        JavascriptExecutor ex = ((JavascriptExecutor) driver);

        percy.snapshot("Homepage Webinar");
        driver.findElement(By.id("signin")).click();
        
        driver.findElement(By.cssSelector("#username input")).sendKeys("fav_user" + Keys.ENTER);
        driver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        percy.snapshot("Sign In Webinar");


        driver.findElement(By.id("login-btn")).click();
        

        SoftAssert softly = new SoftAssert();
        ConfirmationPage page = new HomePage(driver)
                .addProductToCart("iPhone 11")
                .getBag().waitForItemsInBag(1)
                .getBag().proceedToCheckout()
                .enterShippingDetails("firstname", "lastname", "address", "state", "12345");
        percy.snapshot("Checkout Page Webinar");
        Assert.assertTrue(page.isConfirmationDisplayed());

        OrdersPage ordersPage = page.continueShopping().navigateToOrders();

        softly.assertEquals(ordersPage.getItemsFromOrder(), 3);
        softly.assertAll();
    }

}
