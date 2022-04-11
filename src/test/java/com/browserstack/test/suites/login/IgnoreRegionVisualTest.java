package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

public class IgnoreRegionVisualTest extends TestBase {

    private boolean changeCSS = false;

    @Test
    public void ignoreLogo() {
        JavascriptExecutor ex = ((JavascriptExecutor) getDriver());
        String percyCSS = ".Navbar_logo__26S5Y {visibility: hidden}";

        percy.snapshot("Check Homepage with ignored logo", null, 1024, false, percyCSS);
    }

}
