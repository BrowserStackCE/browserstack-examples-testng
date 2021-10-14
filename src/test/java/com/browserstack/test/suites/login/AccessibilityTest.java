package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.List;

public class AccessibilityTest extends TestBase {

    @Test
    public void testForAccessibility() {
        AxeBuilder builder = new AxeBuilder();
        Results results = builder.analyze(getDriver());
        List<Rule> violations = results.getViolations();

        Assertions.assertThat(violations).isEmpty();
    }
}