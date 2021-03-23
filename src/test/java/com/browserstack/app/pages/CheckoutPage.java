package com.browserstack.app.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

    @FindBy(id = "firstNameInput")
    private WebElement firstnameInput;

    @FindBy(id = "lastNameInput")
    private WebElement lastnameInput;

    @FindBy(id = "addressLine1Input")
    private WebElement addressInput;

    @FindBy(id = "provinceInput")
    private WebElement stateInput;

    @FindBy(id = "postCodeInput")
    private WebElement postcodeInput;

    @FindBy(id = "checkout-shipping-continue")
    private WebElement checkoutButton;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public ConfirmationPage enterShippingDetails(String firstname, String lastname, String address, String state, String postcode) {
        firstnameInput.sendKeys(firstname);
        lastnameInput.sendKeys(lastname);
        addressInput.sendKeys(address);
        stateInput.sendKeys(state);
        postcodeInput.sendKeys(postcode);

        checkoutButton.click();
        return new ConfirmationPage(driver);
    }

}
