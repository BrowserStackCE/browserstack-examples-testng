package com.browserstack.examples.providers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.browserstack.examples.config.Platform;
import com.browserstack.examples.config.WebDriverFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class WebDriverDataProvider {

    @DataProvider(name = "webDriverProvider", parallel = true)
    public static Object[] provideWebDrivers(Method testMethod) {
        WebDriverFactory webDriverFactory = WebDriverFactory.getInstance();
        final List<ManagedWebDriver> managedWebDrivers = new ArrayList<>();
        List<Platform> platforms = webDriverFactory.getPlatforms();
        platforms.forEach( p -> {
            managedWebDrivers.add(new ManagedWebDriver(testMethod.getName(), p, webDriverFactory));
        });

        return managedWebDrivers.toArray(new ManagedWebDriver[0]);
    }
}
