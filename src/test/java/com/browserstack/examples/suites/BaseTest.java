package com.browserstack.examples.suites;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.browserstack.examples.core.ManagedWebDriver;
import com.browserstack.examples.core.config.Platform;
import com.browserstack.examples.core.config.WebDriverFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public abstract class BaseTest {

    @DataProvider(parallel = true)
    public static Object[] provideWebDrivers(Method testMethod) {
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

    static final class PlatformAwareIterator implements Iterator<Object[]> {

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Object[] next() {
            return new Object[0];
        }
    }
}
