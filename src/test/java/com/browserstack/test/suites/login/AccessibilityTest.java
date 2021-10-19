package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class AccessibilityTest extends TestBase {

    @Test
    public void testForAccessibility() {
        getDriver().findElement(By.id("signin")).click();

        AxeBuilder builder = new AxeBuilder();
        Results results = builder.analyze(getDriver());
        List<Rule> violations = results.getViolations().stream().filter(c -> c.getId().equals("color-contrast")).collect(Collectors.toList());

        Assertions.assertThat(violations).isEmpty();
    }
}