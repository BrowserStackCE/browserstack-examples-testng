package com.browserstack.examples.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;

import com.browserstack.examples.core.config.Platform;
import com.browserstack.examples.core.config.WebDriverFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class LazyInitWebDriverIterator implements Iterator<Object[]> {

    private final String testMethodName;
    private final List<Platform> platforms;
    private final Object[] otherParams;
    private int platformIdx = 0;

    private LazyInitWebDriverIterator(String testMethodName, List<Platform> platforms, Object[] otherParams) {
        this.testMethodName = testMethodName;
        this.platforms = platforms;
        this.otherParams = otherParams;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return platformIdx < this.platforms.size();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Object[] next() {
        if (this.platformIdx >= this.platforms.size()) {
            throw new NoSuchElementException("No More Platforms configured to create WebDriver for.");
        }

        Object[] returnParams = null;
        if (otherParams != null && otherParams.length > 0) {
            returnParams = Arrays.copyOf(otherParams, otherParams.length + 1);
        } else {
            returnParams = new Object[1];
        }
        Platform platform = this.platforms.get(platformIdx++);
        WebDriver webDriver = WebDriverFactory.getInstance().createWebDriverForPlatform(platform, this.testMethodName);
        returnParams[returnParams.length - 1] = webDriver;

        return returnParams;
    }
}
