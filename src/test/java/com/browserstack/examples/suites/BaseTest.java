package com.browserstack.examples.suites;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import com.browserstack.examples.core.LazyInitWebDriverIterator;
import com.browserstack.examples.core.ManagedWebDriver;
import com.browserstack.examples.core.config.Platform;
import com.browserstack.examples.core.config.WebDriverFactory;
import com.browserstack.examples.core.listeners.WebDriverListener;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
@Listeners({WebDriverListener.class})
public abstract class BaseTest {

    @DataProvider(name="webdriver", parallel = true)
    public static Iterator<Object[]> provideWebDrivers(Method testMethod) {
        LazyInitWebDriverIterator lazyInitWebDriverIterator = new LazyInitWebDriverIterator(testMethod.getName(),
                                                                                            WebDriverFactory.getInstance().getPlatforms(),
                                                                                            new Object[0]);
        return lazyInitWebDriverIterator;
    }

    @DataProvider(name="managedwebdriver", parallel = true)
    public static Object[] provideManagedWebDrivers(Method testMethod) {
        List<ManagedWebDriver> managedWebDrivers = createManagedWebDrivers(testMethod.getName());
        return managedWebDrivers.toArray(new ManagedWebDriver[0]);
    }

    protected static List<ManagedWebDriver> createManagedWebDrivers(String testMethodName) {
        WebDriverFactory webDriverFactory = WebDriverFactory.getInstance();
        final List<ManagedWebDriver> managedWebDrivers = new ArrayList<>();
        List<Platform> platforms = webDriverFactory.getPlatforms();
        platforms.forEach( p -> {
            managedWebDrivers.add(new ManagedWebDriver(testMethodName, p));
        });
        return managedWebDrivers;
    }
}
