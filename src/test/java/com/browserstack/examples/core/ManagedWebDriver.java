package com.browserstack.examples.core;

import org.openqa.selenium.WebDriver;

import com.browserstack.examples.core.config.Platform;
import com.browserstack.examples.core.config.WebDriverFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class ManagedWebDriver {

    private final String testMethodName;
    private final WebDriverFactory webDriverFactory;
    private final Platform platform;
    private WebDriver webDriver;

    public ManagedWebDriver(String testMethodName, Platform platform) {
        this.testMethodName = testMethodName;
        this.platform = platform;
        this.webDriverFactory = WebDriverFactory.getInstance();
    }

    public WebDriver getWebDriver() {
        if (this.webDriver == null) {
            this.webDriver = this.webDriverFactory.createWebDriverForPlatform(platform, testMethodName);
        }
        return this.webDriver;
    }
}
