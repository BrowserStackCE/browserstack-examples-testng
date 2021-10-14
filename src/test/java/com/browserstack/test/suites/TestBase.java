package com.browserstack.test.suites;

import com.browserstack.local.Local;
import com.browserstack.test.utils.DriverUtil;
import com.browserstack.test.utils.ScreenshotListener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
@Listeners({ScreenshotListener.class})
public class TestBase {
    public static final String PATH_TO_TEST_CAPS_JSON = "src/test/resources/conf/capabilities/test_caps.json";
    // ThreadLocal gives the ability to store data individually for the current thread
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final String DOCKER_SELENIUM_HUB_URL = "http://localhost:4444/wd/hub";
    private static final String BROWSERSTACK_HUB_URL = "https://hub.browserstack.com/wd/hub";
    private static final long TIMESTAMP = new Date().getTime();
    private Local local;
    protected WebDriverWait wait;
    private String environment;

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
        if (environment.equalsIgnoreCase("on-prem")) {
            DriverUtil.setDriverPathVariable();
            driver.set(new ChromeDriver());
        } else if (environment.equalsIgnoreCase("remote")) {
            JSONObject profilesJson = (JSONObject) testCapsConfig.get("tests");
            JSONObject envs = (JSONObject) profilesJson.get(testType);

            Map<String, String> commonCapabilities = (Map<String, String>) envs.get("common_caps");
            commonCapabilities.put("name", m.getName());
            commonCapabilities.put("build", commonCapabilities.get("build") + " - " + TIMESTAMP);
            Map<String, String> envCapabilities = (Map<String, String>) ((org.json.simple.JSONArray) envs.get("env_caps")).get(env_cap_id);
            Map<String, String> localCapabilities = (Map<String, String>) envs.get("local_binding_caps");

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.merge(new DesiredCapabilities(commonCapabilities));
            caps.merge(new DesiredCapabilities(envCapabilities));
            if (testType.equals("local")) {
                url = (String) envs.get("application_endpoint");
                caps.merge(new DesiredCapabilities(localCapabilities));
            }

            caps.setCapability("browserstack.user", getUsername(testCapsConfig));
            caps.setCapability("browserstack.key", getAccessKey(testCapsConfig));

            createSecureTunnelIfNeeded(caps, testCapsConfig);

            driver.set(new RemoteWebDriver(new URL(BROWSERSTACK_HUB_URL), caps));
        } else if (environment.equalsIgnoreCase("docker")) {
            DesiredCapabilities dc = new DesiredCapabilities();
            dc.setBrowserName("chrome");
            dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            driver.set(new RemoteWebDriver(new URL(DOCKER_SELENIUM_HUB_URL), dc));
        }
        getDriver().get(url);
        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(getDriver(), 30);
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

    private String getUsername(JSONObject testCapsConfig) {
        String username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) {
            username = testCapsConfig.get("user").toString();
        }
        return username;
    }

    private String getAccessKey(JSONObject testCapsConfig) {
        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = testCapsConfig.get("key").toString();
        }
        return accessKey;
    }

    private void createSecureTunnelIfNeeded(DesiredCapabilities caps, JSONObject testCapsConfig) throws Exception {
        if (caps.getCapability("browserstack.local") != null
                && caps.getCapability("browserstack.local").equals("true")) {
            local = new Local();
            UUID uuid = UUID.randomUUID();
            caps.setCapability("browserstack.localIdentifier", uuid.toString());
            Map<String, String> options = new HashMap<>();
            options.put("key", getAccessKey(testCapsConfig));
            options.put("localIdentifier", uuid.toString());
            local.start(options);
        }
    }

}
