package com.browserstack.test.suites;

import com.browserstack.app.utils.OsUtils;
import com.browserstack.local.Local;
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
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Listeners({ScreenshotListener.class})
public class TestBase {
    // ThreadLocal gives the ability to store data individually for the current thread
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private Local local;
    protected WebDriverWait wait;
    protected String driverLocation = Path.of(System.getProperty("user.dir"), "/src/test/resources/drivers").toString();

    public WebDriver getDriver() {
        return driver.get();
    }

    public String getGeolocation() {
        return "";
    }

    @BeforeMethod
    @Parameters(value = {"environment", "test", "env_cap_id"})
    public void setUp(@Optional("local") String environment, @Optional("single") String test, @Optional("0") int env_cap_id) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/capabilities/test_caps.json"));
        String url = (String) config.get("application_endpoint");
        if (environment.equalsIgnoreCase("local")) {
            if (OsUtils.isMac()) {
                System.setProperty("webdriver.chrome.driver", Path.of(driverLocation, "/chromedriver").toString());
            }
            if (OsUtils.isWindows()) {
                System.setProperty("webdriver.chrome.driver", Path.of(driverLocation, "/chromedriver.exe").toString());
            }
            if (OsUtils.isUnix()) {
                System.setProperty("webdriver.chrome.driver", Path.of(driverLocation, "/chromedriver").toString());
            }
            driver.set(new ChromeDriver());
        } else if (environment.equalsIgnoreCase("remote")) {
            JSONObject profilesJson = (JSONObject) config.get("tests");
            JSONObject envs = (JSONObject) profilesJson.get(test);

            Map<String, String> commonCapabilities = (Map<String, String>) envs.get("common_caps");
            Map<String, String> envCapabilities = (Map<String, String>) ((org.json.simple.JSONArray) envs.get("env_caps")).get(env_cap_id);
            Map<String, String> localCapabilities = (Map<String, String>) envs.get("local_binding_caps");

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.merge(new DesiredCapabilities(commonCapabilities));
            caps.merge(new DesiredCapabilities(envCapabilities));
            if (test.equals("local")) {
                url = (String) envs.get("application_endpoint");
                caps.merge(new DesiredCapabilities(localCapabilities));
            }

            String username = System.getenv("BROWSERSTACK_USERNAME");
            if (username == null) {
                username = (String) config.get("user").toString();
            }
            String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
            if (accessKey == null) {
                accessKey = (String) config.get("key").toString();
            }

            if (!getGeolocation().isEmpty()) {
                caps.setCapability("browserstack.geoLocation", getGeolocation());
            }

            if (caps.getCapability("browserstack.local") != null
                    && caps.getCapability("browserstack.local").equals("true")) {
                local = new Local();
                UUID uuid = UUID.randomUUID();
                caps.setCapability("browserstack.localIdentifier", uuid.toString());
                Map<String, String> options = new HashMap<String, String>();
                options.put("key", accessKey);
                options.put("localIdentifier", uuid.toString());
                local.start(options);
            }

            driver.set(new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + "@hub.browserstack.com/wd/hub"), caps));
        } else if (environment.equalsIgnoreCase("docker")) {
            DesiredCapabilities dc = new DesiredCapabilities();
            dc.setBrowserName("chrome");
            dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc));
        }
        getDriver().get(url);
        wait = new WebDriverWait(getDriver(), 25);
        getDriver().manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if (driver != null) {
            getDriver().quit();
        }
        if (local != null) {
            local.stop();
        }
    }

}
