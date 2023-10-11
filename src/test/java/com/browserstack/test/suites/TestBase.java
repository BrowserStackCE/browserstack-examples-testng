package com.browserstack.test.suites;

import com.browserstack.local.Local;
import com.browserstack.test.utils.DriverUtil;
import com.browserstack.test.utils.ScreenshotListener;
import io.percy.selenium.Percy;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;

@SuppressWarnings("unchecked")
@Listeners({ScreenshotListener.class})
public class TestBase {
    public static final String PATH_TO_TEST_CAPS_JSON = "src/test/resources/conf/capabilities/test_caps.json";
    // ThreadLocal gives the ability to store data individually for the current thread
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final String BROWSERSTACK_HUB_URL = System.getenv("BROWSERSTACK_HUB_URL");
    String username = System.getenv("BROWSERSTACK_USERNAME");
    String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final long TIMESTAMP = new Date().getTime();
    private Local local;
    protected WebDriverWait wait;
    private String environment;
    public static Percy percy;

    public WebDriver getDriver() {
        return driver.get();
    }

    public boolean isRemoteExecution() {
        return environment.equalsIgnoreCase("remote");
    }

    @BeforeMethod
    @Parameters(value = {"environment", "testType", "env_cap_id"})
    public void setUp(@Optional("on-prem") String environment, @Optional("single") String testType, @Optional("0") int env_cap_id, Method m) throws Exception {
        this.environment = environment;
        JSONParser parser = new JSONParser();
        JSONObject testCapsConfig = (JSONObject) parser.parse(new FileReader(PATH_TO_TEST_CAPS_JSON));
        String url = (String) testCapsConfig.get("application_endpoint");
        JSONObject profilesJson = (JSONObject) testCapsConfig.get("tests");
        JSONObject envs = (JSONObject) profilesJson.get(testType);

        HashMap<String, String> browserstackOptions = (HashMap<String, String>) envs.get("common_caps");
        browserstackOptions.put("sessionName", m.getName());
        browserstackOptions.put("buildName", browserstackOptions.get("buildName") + " - " + TIMESTAMP);
        browserstackOptions.put("username", username);
        browserstackOptions.put("accessKey", accessKey);
        HashMap<String, String> envCapabilities = (HashMap<String, String>) ((org.json.simple.JSONArray) envs.get("env_caps")).get(env_cap_id);
        // MutableCapabilities caps = new MutableCapabilities();
        ChromeOptions caps = new ChromeOptions();
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            caps.setCapability(pair.getKey().toString(), pair.getValue());
        }
        caps.setCapability("bstack:options", browserstackOptions);

        driver.set(new RemoteWebDriver(new URL(BROWSERSTACK_HUB_URL), caps));
        percy = new Percy(getDriver());
        getDriver().get(url);
        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if (getDriver() != null) {
            getDriver().quit();
        }
        if (local != null) {
            local.stop();
        }
    }
}
