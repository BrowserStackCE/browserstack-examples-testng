package com.browserstack.examples.core.config;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class WebDriverFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverFactory.class);
    private static final String CAPABILITIES_FILE_PROP = "capabilities.config";
    private static final String DEFAULT_CAPABILITIES_FILE = "capabilities.yml";
    private static final String BROWSERSTACK_USERNAME = "BROWSERSTACK_USERNAME";
    private static final String BROWSERSTACK_ACCESS_KEY = "BROWSERSTACK_ACCESS_KEY";
    private static final String BUILD_ID = "BUILD_ID";
    private static final String DEFAULT_BUILD_NAME = "browserstack-examples-junit5";

    private static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
    private static final String WEBDRIVER_GECKO_DRIVER = "webdriver.gecko.driver";
    private static final String WEBDRIVER_IE_DRIVER = "webdriver.ie.driver";
    private static final String WEBDRIVER_EDGE_DRIVER = "webdriver.edge.driver";

    private static WebDriverFactory instance;

    private final WebDriverConfiguration webDriverConfiguration;
    private final String defaultBuildSuffix;

    private WebDriverFactory() {
        this.defaultBuildSuffix = String.valueOf(System.currentTimeMillis());
        this.webDriverConfiguration = parseWebDriverConfig();
        List<Platform> platforms = webDriverConfiguration.getActivePlatforms();
        startLocalTunnel();
        LOGGER.debug("Running tests on {} active platforms.", platforms.size());
    }

    private boolean isLocalTunnelEnabled() {
        return webDriverConfiguration.getCloudDriverConfig() != null &&
                 webDriverConfiguration.getCloudDriverConfig().getLocalTunnel() != null &&
                 webDriverConfiguration.getCloudDriverConfig().getLocalTunnel().isEnabled();
    }


    public static WebDriverFactory getInstance() {
        if (instance == null) {
            synchronized (WebDriverFactory.class) {
                if (instance == null) {
                    instance = new WebDriverFactory();
                }
            }
        }
        return instance;
    }

    private WebDriverConfiguration parseWebDriverConfig() {
        String capabilitiesConfigFile = System.getProperty(CAPABILITIES_FILE_PROP, DEFAULT_CAPABILITIES_FILE);
        LOGGER.debug("Using capabilities configuration from FILE :: {}", capabilitiesConfigFile);
        URL resourceURL = WebDriverFactory.class.getClassLoader().getResource(capabilitiesConfigFile);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        WebDriverConfiguration webDriverConfiguration;
        try {
            webDriverConfiguration = objectMapper.readValue(resourceURL, WebDriverConfiguration.class);
        } catch (IOException ioe) {
            throw new Error("Unable to parse capabilities file " + capabilitiesConfigFile, ioe);
        }
        return webDriverConfiguration;
    }

    private void startLocalTunnel() {
        if (isLocalTunnelEnabled()) {
            Map<String, String> localOptions = webDriverConfiguration.getCloudDriverConfig().getLocalTunnel().getLocalOptions();
            String accessKey = webDriverConfiguration.getCloudDriverConfig().getAccessKey();
            if (StringUtils.isNoneEmpty(System.getenv(BROWSERSTACK_ACCESS_KEY))) {
                accessKey = System.getenv(BROWSERSTACK_ACCESS_KEY);
            }
            localOptions.put("key", accessKey);
            BrowserStackLocalFactory.createInstance(webDriverConfiguration.getCloudDriverConfig().getLocalTunnel().getLocalOptions());
        }
    }

    public WebDriver createWebDriverForPlatform(Platform platform, String testName) {

        try {
            WebDriver webDriver = null;
            switch (this.webDriverConfiguration.getDriverType()) {
                case onPremDriver:
                    webDriver = createOnPremWebDriver(platform);
                    break;
                case onPremGridDriver:
                    webDriver = createOnPremGridWebDriver(platform);
                    break;
                case cloudDriver:
                    webDriver = createRemoteWebDriver(platform, testName);
            }
            return webDriver;
        } catch (MalformedURLException mae) {
            throw new Error("Unable to create WebDriver", mae);
        }
    }

    public String getTestEndpoint() {
        return this.webDriverConfiguration.getTestEndpoint();
    }

    public DriverType getDriverType() {
        return webDriverConfiguration.getDriverType();
    }

    public List<Platform> getPlatforms() {
        return this.webDriverConfiguration.getActivePlatforms();
    }

    private WebDriver createRemoteWebDriver(Platform platform, String testName) throws MalformedURLException {
        RemoteDriverConfig remoteDriverConfig = this.webDriverConfiguration.getCloudDriverConfig();
        CommonCapabilities commonCapabilities = remoteDriverConfig.getCommonCapabilities();
        DesiredCapabilities platformCapabilities = new DesiredCapabilities();
        if (StringUtils.isNotEmpty(platform.getDevice())) {
            platformCapabilities.setCapability("device", platform.getDevice());
        }
        platformCapabilities.setCapability("browser", platform.getBrowser());
        platformCapabilities.setCapability("browser_version", platform.getBrowserVersion());
        platformCapabilities.setCapability("os", platform.getOs());
        platformCapabilities.setCapability("os_version", platform.getOsVersion());
        platformCapabilities.setCapability("name", testName);
        platformCapabilities.setCapability("project", commonCapabilities.getProject());
        platformCapabilities.setCapability("build", createBuildName(commonCapabilities.getBuildPrefix()));
        if (commonCapabilities.getCapabilities() != null) {
            commonCapabilities.getCapabilities().getCapabilityMap().forEach(platformCapabilities::setCapability);
        }
        if (platform.getCapabilities() != null) {
            platform.getCapabilities().getCapabilityMap().forEach(platformCapabilities::setCapability);
        }
        String user = remoteDriverConfig.getUser();
        if (StringUtils.isNoneEmpty(System.getenv(BROWSERSTACK_USERNAME))) {
            user = System.getenv(BROWSERSTACK_USERNAME);
        }
        String accessKey = remoteDriverConfig.getAccessKey();
        if (StringUtils.isNoneEmpty(System.getenv(BROWSERSTACK_ACCESS_KEY))) {
            accessKey = System.getenv(BROWSERSTACK_ACCESS_KEY);
        }
        platformCapabilities.setCapability("browserstack.user", user);
        platformCapabilities.setCapability("browserstack.key", accessKey);
        if (isLocalTunnelEnabled()) {
            platformCapabilities.setCapability("browserstack.localIdentifier", BrowserStackLocalFactory.getInstance().getLocalIdentifier());
        }
        LOGGER.debug("Initialising RemoteWebDriver with capabilities : {}",platformCapabilities);
        return new RemoteWebDriver(new URL(remoteDriverConfig.getHubUrl()), platformCapabilities);
    }

    private WebDriver createOnPremGridWebDriver(Platform platform) throws MalformedURLException {
        DesiredCapabilities capabilities;
        switch (BrowserType.valueOf(platform.getBrowser())) {
            case chrome:
                capabilities = new DesiredCapabilities(new ChromeOptions());
                break;
            case firefox:
                capabilities = new DesiredCapabilities(new FirefoxOptions());
                break;
            default:
                throw new RuntimeException("Unsupported Browser : " + platform.getBrowser());
        }
        if (platform.getCapabilities() != null) {
            platform.getCapabilities().getCapabilityMap().forEach(capabilities::setCapability);
        }
        LOGGER.debug("Initialising OnPremGridWebDriver with capabilities : {}",capabilities);
        return new RemoteWebDriver(new URL(this.webDriverConfiguration.getOnPremGridDriverConfig().getHubUrl()), capabilities);
    }

    private WebDriver createOnPremWebDriver(Platform platform) {
        WebDriver webDriver = null;
        switch (BrowserType.valueOf(platform.getBrowser())) {
            case chrome:
                System.setProperty(WEBDRIVER_CHROME_DRIVER, Paths.get(platform.getDriverPath()).toString());
                ChromeOptions chromeOptions = new ChromeOptions();
                if (platform.getCapabilities() != null) {
                    platform.getCapabilities().getCapabilityMap().forEach(chromeOptions::setCapability);
                }
                LOGGER.debug("Initialising ChromeDriver with capabilities : {}",chromeOptions);
                webDriver = new ChromeDriver(chromeOptions);
                break;
            case firefox:
                System.setProperty(WEBDRIVER_GECKO_DRIVER, Paths.get(platform.getDriverPath()).toString());
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (platform.getCapabilities() != null) {
                    platform.getCapabilities().getCapabilityMap().forEach(firefoxOptions::setCapability);
                }
                LOGGER.debug("Initialising FirefoxDriver with capabilities : {}",firefoxOptions);
                webDriver = new FirefoxDriver(firefoxOptions);
                break;
            case ie:
                System.setProperty(WEBDRIVER_IE_DRIVER, Paths.get(platform.getDriverPath()).toString());
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                if (platform.getCapabilities() != null) {
                    platform.getCapabilities().getCapabilityMap().forEach(internetExplorerOptions::setCapability);
                }
                LOGGER.debug("Initialising InternetExplorerDriver with capabilities : {}",internetExplorerOptions);
                webDriver = new InternetExplorerDriver(internetExplorerOptions);
                break;
            case edge:
                System.setProperty(WEBDRIVER_EDGE_DRIVER, Paths.get(platform.getDriverPath()).toString());
                EdgeOptions edgeOptions = new EdgeOptions();
                if (platform.getCapabilities() != null) {
                    platform.getCapabilities().getCapabilityMap().forEach(edgeOptions::setCapability);
                }
                LOGGER.debug("Initialising EdgeDriver with capabilities : {}",edgeOptions);
                webDriver = new EdgeDriver(edgeOptions);
                break;
            case safari:
                SafariOptions safariOptions = new SafariOptions();
                if (platform.getCapabilities() != null) {
                    platform.getCapabilities().getCapabilityMap().forEach(safariOptions::setCapability);
                }
                LOGGER.debug("Initialising SafariDriver with capabilities : {}",safariOptions);
                webDriver = new SafariDriver(safariOptions);
                break;
            case opera:
                OperaOptions operaOptions = new OperaOptions();
                if (platform.getCapabilities() != null) {
                    platform.getCapabilities().getCapabilityMap().forEach(operaOptions::setCapability);
                }
                LOGGER.debug("Initialising OperaDriver with capabilities : {}",operaOptions);
                webDriver = new OperaDriver(operaOptions);
                break;
        }
        return webDriver;
    }

    private String createBuildName(String buildPrefix) {
        if (StringUtils.isEmpty(buildPrefix)) {
            buildPrefix = DEFAULT_BUILD_NAME;
        }
        String buildName = buildPrefix;

        String buildSuffix = this.defaultBuildSuffix;
        if (StringUtils.isNotEmpty(System.getenv(BUILD_ID))) {
            buildSuffix = System.getenv(BUILD_ID);
        }
        return buildName + "-" + buildSuffix;
    }

    public String getSuiteName(){
        return webDriverConfiguration.getName();
    }

}
