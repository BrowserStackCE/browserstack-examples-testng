package com.browserstack.examples.providers;

import org.openqa.selenium.WebDriver;

import com.browserstack.examples.config.Platform;
import com.browserstack.examples.config.WebDriverFactory;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
@Getter
public class ManagedWebDriver {

    private final String testMethodName;
    private final WebDriverFactory webDriverFactory;
    private final Platform platform;
    private WebDriver webDriver;

    public ManagedWebDriver(String testMethodName, Platform platform, WebDriverFactory webDriverFactory) {
        this.testMethodName = testMethodName;
        this.platform = platform;
        this.webDriverFactory = webDriverFactory;
    }

    public WebDriver getWebDriver() {
        if (this.webDriver == null) {
            this.webDriver = this.webDriverFactory.createWebDriverForPlatform(platform, testMethodName);
        }
        return this.webDriver;
    }
}
